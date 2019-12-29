package cn.mypandora.springboot.modular.system.service.impl;

import cn.mypandora.springboot.core.exception.CustomException;
import cn.mypandora.springboot.modular.system.mapper.DepartmentMapper;
import cn.mypandora.springboot.modular.system.mapper.DepartmentUserMapper;
import cn.mypandora.springboot.modular.system.model.po.BaseEntity;
import cn.mypandora.springboot.modular.system.model.po.Department;
import cn.mypandora.springboot.modular.system.service.DepartmentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * DepartmentServiceImpl
 *
 * @author hankaibo
 * @date 2019/9/25
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentMapper departmentMapper;
    private DepartmentUserMapper departmentUserMapper;

    @Autowired
    public DepartmentServiceImpl(DepartmentMapper departmentMapper, DepartmentUserMapper departmentUserMapper) {
        this.departmentMapper = departmentMapper;
        this.departmentUserMapper = departmentUserMapper;
    }

    @Override
    public List<Department> listDepartment(Integer status, Long userId) {
        // 获取用户的所有部门并过滤掉子孙部门，以减少后面重复部门的获取。
        Map<String, Number> map = new HashMap<>(2);
        map.put("userId", userId);
        map.put("status", status);
        List<Department> allDepartmentList = departmentMapper.listByUserId(map);

        List<Department> departmentList = listTopAncestryDepartment(allDepartmentList);
        // 自身
        Set<Department> departmentSet = new HashSet<>(departmentList);
        // 所有后代部门(用户多部门情况)
        for (Department department : departmentList) {
            // 将自己上级置为空，方便工具类构建树。
            department.setParentId(null);
            Map<String, Number> descendantMap = new HashMap<>(2);
            descendantMap.put("id", department.getId());
            descendantMap.put("status", status);
            List<Department> departmentDescendantList = departmentMapper.listDescendants(descendantMap);
            departmentSet.addAll(departmentDescendantList);
        }
        return departmentSet.stream().sorted(Comparator.comparing(Department::getLft)).collect(Collectors.toList());
    }

    @Override
    public List<Department> listDepartmentChildren(Long id, Integer status, Long userId) {
        // 获取当前用户的部门范围
        List<Department> allDepartmentList = listDepartment(status, userId);
        // 查询子部门并过滤出合适数据
        Map<String, Number> map = new HashMap<>(2);
        map.put("id", id);
        map.put("status", status);
        List<Department> departmentList = departmentMapper.listChildren(map);
        return departmentList.stream().filter(allDepartmentList::contains).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addDepartment(Department department, Long userId) {
        // 获取父部门范围并判断(只能添加到同级及下级部门)
        List<Department> departmentList = listDepartment(1, userId);
        if (departmentList.stream().noneMatch(item -> item.getId().equals(department.getParentId()))) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "父级部门选择错误。");
        }
        // 添加
        Department parentDepartment = getDepartmentById(department.getParentId(), userId);
        LocalDateTime now = LocalDateTime.now();
        department.setCreateTime(now);
        department.setLft(parentDepartment.getRgt());
        department.setRgt(parentDepartment.getRgt() + 1);
        department.setLevel(parentDepartment.getLevel() + 1);

        Map<String, Number> map = new HashMap<>(2);
        map.put("id", department.getParentId());
        map.put("amount", 2);
        departmentMapper.lftAdd(map);
        departmentMapper.rgtAdd(map);
        departmentMapper.insert(department);
        departmentMapper.parentRgtAdd(map);
    }

    @Override
    public Department getDepartmentById(Long id, Long userId) {
        // 获取部门范围防止用户查看自身权限外的部门信息
        List<Department> departmentList = listDepartment(1, userId);
        if (departmentList.stream().noneMatch(item -> item.getId().equals(id))) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "无法查看该部门。");
        }
        // 查询
        Department department = new Department();
        department.setId(id);
        department = departmentMapper.selectByPrimaryKey(department);
        if (department == null) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "部门不存在。");
        }
        return department;
    }

    @Override
    public void updateDepartment(Department department, Long userId) {
        // 修改部门时，必须保证父部门存在，并只能更改自己权限范围内的部门
        List<Department> departmentList = listDepartment(1, userId);
        if (departmentList.stream().noneMatch(item -> item.getId().equals(department.getParentId()))) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "父级部门错误。");
        }
        // 修改部门时，不可以指定自己的下级为父部门。
        if (!isCanUpdateParent(department)) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "不可以选择子部门作为自己的父级。");
        }
        Department info = getDepartmentById(department.getId(), userId);
        // 如果父级部门相等，则直接修改其它属性
        if (info.getParentId().equals(department.getParentId())) {
            departmentMapper.updateByPrimaryKeySelective(department);
        } else {
            // 要修改的部门及子孙部门共多少个
            List<Long> updateIdList = listDescendantId(department.getId());
            int departmentNum = updateIdList.size();
            // 新父部门
            Department parentDepartment = getDepartmentById(department.getParentId(), userId);
            int rgtParent = parentDepartment.getRgt();
            int lftCurrent = info.getLft();
            // 被修改部门及子孙部门左右值修改
            Map<String, Object> updateMap = new HashMap<>(2);
            updateMap.put("idList", updateIdList);
            updateMap.put("amount", rgtParent - lftCurrent);
            departmentMapper.selfAndDescendant(updateMap);
            // 被修改部门的父部门右值修改
            Map<String, Number> parentMap = new HashMap<>(2);
            parentMap.put("id", department.getParentId());
            parentMap.put("amount", departmentNum * 2);
            departmentMapper.parentRgtAdd(parentMap);
            // 计算从那个值开始调整左右值
            Map<String, Number> map = new HashMap<>(2);
            map.put("id", lftCurrent - rgtParent > 0 ? department.getParentId() : info.getParentId());
            map.put("amount", departmentNum * 2);
            departmentMapper.lftAdd(map);
            departmentMapper.rgtAdd(map);
            // 修改本身
            LocalDateTime now = LocalDateTime.now();
            department.setUpdateTime(now);
            departmentMapper.updateByPrimaryKeySelective(department);
        }
    }

    @Override
    public void enableDepartment(Long id, Integer status, Long userId) {
        List<Department> departmentList = listDepartment(null, userId);
        List<Long> idList = listDescendantId(id);
        List<Long> list = departmentList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        if (!list.retainAll(idList)) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "部门错误。");
        }
        Map<String, Object> map = new HashMap<>(2);
        map.put("idList", idList);
        map.put("status", status);
        departmentMapper.enableDescendants(map);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteDepartment(Long id, Long userId) {
        // 获得可删除的部门范围
        List<Department> departmentList = listDepartment(null, userId);
        List<Long> list = departmentList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        if (!list.contains(id)) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "部门错误。");
        }

        Department department = new Department();
        department.setId(id);
        // 先求出要删除的部门的所有信息，利用左值与右值计算出要删除的部门数量。
        // 删除部门数=(部门右值-部门左值+1)/2
        Department info = departmentMapper.selectByPrimaryKey(department);
        int deleteAmount = info.getRgt() - info.getLft() + 1;
        // 更新此部门之后的相关部门左右值
        Map<String, Number> map = new HashMap<>(2);
        map.put("id", id);
        map.put("amount", -deleteAmount);
        departmentMapper.lftAdd(map);
        departmentMapper.rgtAdd(map);
        // 求出要删除的部门所有子孙部门
        List<Long> idList = listDescendantId(id);
        String ids = StringUtils.join(idList, ',');
        // 批量删除部门及子孙部门
        departmentMapper.deleteByIds(ids);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void moveDepartment(Long sourceId, Long targetId, Long userId) {
        List<Department> departmentList = listDepartment(null, userId);
        List<Long> idList = new ArrayList<>();
        idList.add(sourceId);
        idList.add(targetId);
        List<Long> list = departmentList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        if (!list.retainAll(idList)) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "部门错误。");
        }

        // 先取出源部门与目标部门两者的信息
        Department sourceDepartment = new Department();
        Department targetDepartment = new Department();

        sourceDepartment.setId(sourceId);
        targetDepartment.setId(targetId);

        Department sourceInfo = departmentMapper.selectByPrimaryKey(sourceDepartment);
        Department targetInfo = departmentMapper.selectByPrimaryKey(targetDepartment);

        int sourceAmount = sourceInfo.getRgt() - sourceInfo.getLft() + 1;
        int targetAmount = targetInfo.getRgt() - targetInfo.getLft() + 1;

        List<Long> sourceIdList = listDescendantId(sourceId);
        List<Long> targetIdList = listDescendantId(targetId);

        // 确定方向，目标大于源：下称；反之：上移。
        if (targetInfo.getRgt() > sourceInfo.getRgt()) {
            sourceAmount *= -1;
        } else {
            targetAmount *= -1;
        }
        // 源部门及子孙部门左右值 targetAmount
        Map<String, Object> sourceMap = new HashMap<>(2);
        sourceMap.put("idList", sourceIdList);
        sourceMap.put("amount", targetAmount);
        departmentMapper.selfAndDescendant(sourceMap);
        // 目标部门及子孙部门左右值 sourceAmount
        Map<String, Object> targetMap = new HashMap<>(2);
        targetMap.put("idList", targetIdList);
        targetMap.put("amount", sourceAmount);
        departmentMapper.selfAndDescendant(targetMap);
    }

    @Override
    public int countUserById(Long id) {
        return departmentUserMapper.countUserByDepartmentId(id);
    }

    /**
     * 获取此部门及其子孙部门的id
     *
     * @param id 部门主键id
     * @return 部门主键id集合
     */
    private List<Long> listDescendantId(Long id) {
        Map<String, Number> map = new HashMap<>(1);
        map.put("id", id);
        List<Department> departmentList = departmentMapper.listDescendants(map);
        List<Long> idList = departmentList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        idList.add(id);
        return idList;
    }

    /**
     * 获取各部门最顶级的祖先部门。
     * 如果一个用户在父级部门、本级部门、子级部门都存在，则只过滤出父级部门，以减少后边重复查询。
     *
     * @param departmentList 部门列表
     * @return 用户所在各部门的最顶级部门。
     */
    private List<Department> listTopAncestryDepartment(List<Department> departmentList) {
        List<Department> result = new ArrayList<>();
        if (departmentList.size() == 1) {
            return departmentList;
        }
        for (Department department : departmentList) {
            if (departmentList
                    .stream()
                    .filter(item -> item.getLevel() < department.getLevel())
                    .noneMatch(item -> item.getLft() < department.getLft() && item.getRgt() > department.getRgt())) {
                result.add(department);
            }
        }
        return result;
    }

    /**
     * 防止更新部门时，指定自己的下级部门作为自己的父级部门。
     *
     * @param department 部门
     * @return true可以更新；false不可以更新
     */
    private boolean isCanUpdateParent(Department department) {
        Department child = new Department();
        child.setId(department.getId());
        Department childDepartment = departmentMapper.selectByPrimaryKey(child);

        Department parent = new Department();
        parent.setId(department.getParentId());
        Department parentDepartment = departmentMapper.selectByPrimaryKey(parent);
        return !(parentDepartment.getLft() >= childDepartment.getLft() && parentDepartment.getRgt() <= childDepartment.getRgt());
    }

}
