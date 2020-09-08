package cn.mypandora.springboot.modular.system.service.impl;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mypandora.springboot.config.exception.BusinessException;
import cn.mypandora.springboot.config.exception.EntityNotFoundException;
import cn.mypandora.springboot.core.enums.StatusEnum;
import cn.mypandora.springboot.modular.system.mapper.DepartmentMapper;
import cn.mypandora.springboot.modular.system.mapper.DepartmentUserMapper;
import cn.mypandora.springboot.modular.system.model.po.BaseEntity;
import cn.mypandora.springboot.modular.system.model.po.Department;
import cn.mypandora.springboot.modular.system.service.DepartmentService;

/**
 * DepartmentServiceImpl
 *
 * @author hankaibo
 * @date 2019/9/25
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentMapper departmentMapper;
    private final DepartmentUserMapper departmentUserMapper;

    @Autowired
    public DepartmentServiceImpl(DepartmentMapper departmentMapper, DepartmentUserMapper departmentUserMapper) {
        this.departmentMapper = departmentMapper;
        this.departmentUserMapper = departmentUserMapper;
    }

    @Override
    public List<Department> listDepartment(Integer status, Long userId) {
        // 获取用户的所有部门并过滤掉子孙部门，以减少后面重复部门的获取。
        List<Department> allDepartmentList = departmentMapper.listByUserId(userId, status);
        List<Department> departmentList = listTopAncestryDepartment(allDepartmentList);

        // 自身
        Set<Department> departmentSet = new HashSet<>(departmentList);

        // 所有后代部门(用户多部门情况)
        for (Department department : departmentList) {
            Long id = department.getId();
            List<Department> departmentDescendantList = departmentMapper.listDescendants(id, status);
            departmentSet.addAll(departmentDescendantList);
        }

        return departmentSet.stream().sorted(Comparator.comparing(Department::getLft)).collect(Collectors.toList());
    }

    @Override
    public List<Department> listChildrenDepartment(Long id, Integer status, Long userId) {
        // 获取当前用户的部门范围
        List<Department> allDepartmentList = listDepartment(null, userId);

        // 查询子部门并过滤出合适数据
        List<Department> departmentList = departmentMapper.listChildren(id, status);
        return departmentList.stream().filter(allDepartmentList::contains).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addDepartment(Department department, Long userId) {
        // 获取父部门范围并判断
        List<Department> departmentList = listDepartment(null, userId);
        if (departmentList.stream().noneMatch(item -> item.getId().equals(department.getParentId()))) {
            throw new BusinessException(Department.class, "父级部门选择错误。");
        }

        // 添加到父部门末尾
        Long parentId = department.getParentId();
        Department parentDepartment = getDepartmentById(parentId, userId);
        LocalDateTime now = LocalDateTime.now();
        department.setCreateTime(now);
        department.setLft(parentDepartment.getRgt());
        department.setRgt(parentDepartment.getRgt() + 1);
        department.setLevel(parentDepartment.getLevel() + 1);
        department.setIsUpdate(1);

        int amount = 2;
        // 将树形结构中所有大于父部门右值的左部门+2
        departmentMapper.lftAdd(parentId, amount, null);
        // 将树形结构中所有大于父部门右值的右部门+2
        departmentMapper.rgtAdd(parentId, amount, null);
        // 插入新部门
        departmentMapper.insert(department);
    }

    @Override
    public Department getDepartmentById(Long id, Long userId) {
        // 获取部门范围防止用户查看自身权限外的部门信息
        List<Department> departmentList = listDepartment(null, userId);
        if (departmentList.stream().noneMatch(item -> item.getId().equals(id))) {
            throw new EntityNotFoundException(Department.class, "无法查看该部门。");
        }

        // 查询
        Department info = departmentMapper.selectByPrimaryKey(id);
        if (info == null) {
            throw new EntityNotFoundException(Department.class, "部门不存在。");
        }

        return info;
    }

    /**
     * 部门修改如果涉及父级部门变动则非常复杂，在这里先使用isUpdate锁住被移动部门，保证不修改它的左右值，方便后续操作。
     *
     * @param department
     *            部门信息
     * @param userId
     *            用户id
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateDepartment(Department department, Long userId) {
        // 有用户部门，不可以修改为禁用。
        if (department.getStatus().equals(0)) {
            int count = departmentUserMapper.countUserByDepartmentId(department.getId());
            if (count > 0) {
                throw new BusinessException(Department.class, "该部门或子部门有关联用户，不可以禁用。");
            }
        }

        // 修改部门时，必须保证父部门存在，并只能更改为自己权限范围内的部门
        List<Department> departmentList = listDepartment(null, userId);
        if (departmentList.stream().noneMatch(item -> item.getId().equals(department.getParentId()))) {
            throw new BusinessException(Department.class, "父级部门错误。");
        }

        // 修改部门时，不可以指定自己的下级为父部门。
        if (!isCanUpdateParent(department)) {
            throw new BusinessException(Department.class, "不可以选择子部门作为自己的父级。");
        }

        // 修改部门时，不可以修改自己权限之外的部门。
        if (departmentList.stream().noneMatch(item -> item.getId().equals(department.getId()))) {
            throw new BusinessException(Department.class, "部门错误。");
        }

        // 修改部门
        LocalDateTime now = LocalDateTime.now();
        department.setUpdateTime(now);

        // 如果父级部门相等，则直接修改其它属性
        Department info = getDepartmentById(department.getId(), userId);
        if (!info.getParentId().equals(department.getParentId())) {
            // 求出新旧两个父部门的最近共同祖先部门，减小修改范围。
            Department newParentDepartment = getDepartmentById(department.getParentId(), userId);
            Department oldParentDepartment = getDepartmentById(info.getParentId(), userId);
            Department commonAncestry = getCommonAncestry(newParentDepartment, oldParentDepartment);

            // 避免下面修改了共同祖先部门的右部门值。
            int range = commonAncestry.getRgt() + 1;

            // 要修改的部门及子孙部门共多少个
            List<Long> updateIdList = listDescendantId(department.getId());
            int departmentNum = updateIdList.size();

            // 首先锁定被修改部门及子孙部门，保证左右值不会被下面操作修改。
            departmentMapper.locking(updateIdList, 0);

            // 先将修改部门摘出来，它之后的部门部门值修改
            Long oldId = info.getId();
            int oldAmount = departmentNum * -2;
            departmentMapper.lftAdd(oldId, oldAmount, range);
            departmentMapper.rgtAdd(oldId, oldAmount, range);

            // 将修改部门插入到新父部门之后左右值修改
            Long newParentId = newParentDepartment.getId();
            int newAmount = departmentNum * 2;
            departmentMapper.lftAdd(newParentId, newAmount, range);
            departmentMapper.rgtAdd(newParentId, newAmount, range);

            // 被修改部门及子孙部门左右值修改
            departmentMapper.locking(updateIdList, 1);
            int amount = getDepartmentById(department.getParentId(), userId).getRgt() - info.getRgt() - 1;
            int level = newParentDepartment.getLevel() + 1 - info.getLevel();
            departmentMapper.selfAndDescendant(updateIdList, amount, level);

            // 修改本身
            department.setLevel(newParentDepartment.getLevel() + 1);
        }

        departmentMapper.updateByPrimaryKeySelective(department);
    }

    @Override
    public void enableDepartment(Long id, Integer status, Long userId) {
        // 本部门及子孙部门是否有用户判断
        int count = departmentUserMapper.countUserByDepartmentId(id);
        if (count > 0) {
            throw new BusinessException(Department.class, "该部门或子部门有关联用户，不可以禁用。");
        }

        // 修改本部门及子孙部门状态
        List<Department> departmentList = listDepartment(null, userId);
        List<Long> idList = listDescendantId(id);
        List<Long> allIdList = departmentList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        if (!allIdList.retainAll(idList)) {
            throw new BusinessException(Department.class, "部门错误。");
        }
        departmentMapper.enableDescendants(idList, status);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteDepartment(Long id, Long userId) {
        // 本部门及子孙部门是否有用户判断
        int count = departmentUserMapper.countUserByDepartmentId(id);
        if (count > 0) {
            throw new BusinessException(Department.class, "该部门或子部门有关联用户，不可以删除。");
        }

        // 获得可删除的部门范围
        List<Department> departmentList = listDepartment(null, userId);
        List<Long> allIdList = departmentList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        if (!allIdList.contains(id)) {
            throw new BusinessException(Department.class, "部门错误。");
        }

        // 求出要删除的部门所有子孙部门
        List<Long> idList = listDescendantId(id);
        String ids = StringUtils.join(idList, ',');

        // 先求出要删除的部门的所有信息，利用左值与右值计算出要删除的部门数量。
        // 删除部门数=(部门右值-部门左值+1)/2
        Department info = departmentMapper.selectByPrimaryKey(id);
        int deleteAmount = info.getRgt() - info.getLft() + 1;

        // 更新此部门之后的相关部门左右值
        departmentMapper.lftAdd(id, -deleteAmount, null);
        departmentMapper.rgtAdd(id, -deleteAmount, null);

        // 批量删除部门及子孙部门
        departmentMapper.deleteByIds(ids);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void moveDepartment(Long sourceId, Long targetId, Long userId) {
        // 部门范围判断
        List<Department> departmentList = listDepartment(null, userId);

        Optional<Department> optionalSource =
            departmentList.stream().filter(it -> it.getId().equals(sourceId)).findFirst();
        Department sourceInfo = optionalSource.orElse(null);

        Optional<Department> optionalTarget =
            departmentList.stream().filter(it -> it.getId().equals(targetId)).findFirst();
        Department targetInfo = optionalTarget.orElse(null);

        if (null == sourceInfo || null == targetInfo) {
            throw new BusinessException(Department.class, "所选部门错误，超出权限范围。");
        }

        List<Long> allIdList =
            departmentList.stream().sorted(Comparator.comparing(Department::getLevel).thenComparing(Department::getLft))
                .map(BaseEntity::getId).collect(Collectors.toList());
        // 同层级、相邻
        if (!(sourceInfo.getLevel().equals(targetInfo.getLevel()))
            || !(Math.abs(allIdList.indexOf(sourceId) - allIdList.indexOf(targetId)) == 1)) {
            throw new BusinessException(Department.class, "所选部门错误，不是同级相邻部门。");
        }

        int sourceAmount = sourceInfo.getRgt() - sourceInfo.getLft() + 1;
        int targetAmount = targetInfo.getRgt() - targetInfo.getLft() + 1;

        List<Long> sourceIdList = listDescendantId(sourceId);
        List<Long> targetIdList = listDescendantId(targetId);

        // 确定方向，目标大于源：下移；反之：上移。
        if (targetInfo.getRgt() > sourceInfo.getRgt()) {
            sourceAmount *= -1;
        } else {
            targetAmount *= -1;
        }
        // 源部门及子孙部门左右值 targetAmount
        departmentMapper.selfAndDescendant(sourceIdList, targetAmount, 0);
        // 目标部门及子孙部门左右值 sourceAmount
        departmentMapper.selfAndDescendant(targetIdList, sourceAmount, 0);
    }

    /**
     * 获取此部门及其子孙部门的id。
     *
     * @param id
     *            部门主键id
     * @return 部门主键id集合
     */
    private List<Long> listDescendantId(Long id) {
        List<Department> departmentList = departmentMapper.listDescendants(id, null);
        List<Long> idList = departmentList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        idList.add(id);
        return idList;
    }

    /**
     * 获取各部门最顶级的祖先部门。 如果一个用户在父级部门、本级部门、子级部门都存在，则只过滤出父级部门，以减少后边重复查询。
     *
     * @param departmentList
     *            部门列表
     * @return 用户所在各部门的最顶级部门。
     */
    private List<Department> listTopAncestryDepartment(List<Department> departmentList) {
        List<Department> result = new ArrayList<>();
        if (departmentList.size() == 1) {
            return departmentList;
        }
        for (Department department : departmentList) {
            if (departmentList.stream().filter(item -> item.getLevel() < department.getLevel())
                .noneMatch(item -> item.getLft() < department.getLft() && item.getRgt() > department.getRgt())) {
                result.add(department);
            }
        }
        return result;
    }

    /**
     * 防止更新部门时，指定自己的下级部门作为自己的父级部门。
     *
     * @param department
     *            部门
     * @return true可以更新；false不可以更新
     */
    private boolean isCanUpdateParent(Department department) {
        Department childDepartment = departmentMapper.selectByPrimaryKey(department.getId());

        Department parentDepartment = departmentMapper.selectByPrimaryKey(department.getParentId());
        return !(parentDepartment.getLft() >= childDepartment.getLft()
            && parentDepartment.getRgt() <= childDepartment.getRgt());
    }

    /**
     * 获取两个部门最近的共同祖先部门。
     *
     * @param department1
     *            第一个部门
     * @param department2
     *            第二个部门
     * @return 最近的祖先部门
     */
    private Department getCommonAncestry(Department department1, Department department2) {
        // 首先判断两者是否是包含关系
        if (department1.getLft() < department2.getLft() && department1.getRgt() > department2.getRgt()) {
            return department1;
        }
        if (department2.getLft() < department1.getLft() && department2.getRgt() > department1.getRgt()) {
            return department2;
        }
        // 两者没有包含关系的情况下
        Long newId = department1.getId();
        List<Department> newParentAncestries = departmentMapper.listAncestries(newId, StatusEnum.ENABLED.getValue());
        if (newParentAncestries.size() == 0) {
            newParentAncestries.add(department1);
        }

        Long oldId = department2.getId();
        List<Department> oldParentAncestries = departmentMapper.listAncestries(oldId, StatusEnum.ENABLED.getValue());
        if (oldParentAncestries.size() == 0) {
            oldParentAncestries.add(department2);
        }

        Comparator<Department> comparator = Comparator.comparing(Department::getLft);
        return newParentAncestries.stream().filter(oldParentAncestries::contains).max(comparator)
            .orElseThrow(RuntimeException::new);
    }

}
