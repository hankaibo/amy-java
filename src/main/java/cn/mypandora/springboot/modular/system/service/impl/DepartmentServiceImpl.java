package cn.mypandora.springboot.modular.system.service.impl;

import cn.mypandora.springboot.modular.system.mapper.DepartmentMapper;
import cn.mypandora.springboot.modular.system.mapper.DepartmentUserMapper;
import cn.mypandora.springboot.modular.system.model.BaseEntity;
import cn.mypandora.springboot.modular.system.model.po.Department;
import cn.mypandora.springboot.modular.system.service.DepartmentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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
    public List<Department> listAll(Integer status) {
        return departmentMapper.listAll(status);
    }

    @Override
    public List<Department> listChildren(Long id, Integer status) {
        return departmentMapper.listChildren(id, status);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addDepartment(Department department) {
        // 如果parentId为空，那么就创建为一个新树的根节点，parentId是null，level是1。
        if (department.getParentId() == null || department.getParentId() < 1) {
            department.setLft(1);
            department.setRgt(2);
            department.setLevel(1);
            department.setParentId(null);
        } else {
            Department info = getDepartmentById(department.getParentId());
            department.setLft(info.getRgt());
            department.setRgt(info.getRgt() + 1);
            department.setLevel(info.getLevel() + 1);
        }
        LocalDateTime now = LocalDateTime.now();
        department.setCreateTime(now);
        departmentMapper.lftAdd(department.getParentId(), 2);
        departmentMapper.rgtAdd(department.getParentId(), 2);
        departmentMapper.insert(department);
        departmentMapper.parentRgtAdd(department.getParentId(), 2);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteDepartment(Long id) {
        Department department = new Department();
        department.setId(id);
        // 先求出要删除的节点的所有信息，利用左值与右值计算出要删除的节点数量。
        // 删除节点数=(节点右值-节点左值+1)/2
        Department info = departmentMapper.selectByPrimaryKey(department);
        int deleteAmount = info.getRgt() - info.getLft() + 1;
        // 更新此节点之后的相关节点左右值
        departmentMapper.lftAdd(id, -deleteAmount);
        departmentMapper.rgtAdd(id, -deleteAmount);
        // 求出要删除的节点所有子孙节点
        List<Long> idList = listDescendantId(id);
        String ids = StringUtils.join(idList, ',');
        // 批量删除节点及子孙节点
        departmentMapper.deleteByIds(ids);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void moveDepartment(Long sourceId, Long targetId) {
        // 先取出源节点与目标节点两者的信息
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
        // 源节点及子孙节点左右值 targetAmount
        departmentMapper.selfAndDescendant(sourceIdList, targetAmount);
        // 目标节点及子孙节点左右值 sourceAmount
        departmentMapper.selfAndDescendant(targetIdList, sourceAmount);
    }

    @Override
    public Department getDepartmentById(Long id) {
        Department department = new Department();
        department.setId(id);
        return departmentMapper.selectByPrimaryKey(department);
    }

    @Override
    public void updateDepartment(Department department) {
        LocalDateTime now = LocalDateTime.now();
        department.setUpdateTime(now);
        departmentMapper.updateByPrimaryKeySelective(department);
    }

    @Override
    public void enableDepartment(Long id, Integer status) {
        List<Long> idList = listDescendantId(id);
        departmentMapper.enableDescendants(idList, status);
    }

    @Override
    public int countUserById(Long id) {
        return departmentUserMapper.countUserByDepartmentId(id);
    }

    @Override
    public boolean isExistParentId(Long parentId) {
        // 默认情况下，数据结构为单树(只有一条数据的parentId可为空)，数据库表初始化后有一条数据。
        // 为了避免没有初始化数据表就操作造成问题（比如多条数据的parentId为空），处理下特殊情况。
        // 如果parentId不为空，并存在于数据库表中，为真；
        // 如果parentId为空，并且数据库也为空，为真；
        // 其它为假。
        if (parentId != null) {
            Department department = getDepartmentById(parentId);
            return department != null;
        } else {
            int count = listAll(null).size();
            return count == 0;
        }
    }

    /**
     * 获取此节点及其子孙节点的id
     *
     * @param id 节点
     * @return 节点集合
     */
    private List<Long> listDescendantId(Long id) {
        List<Department> departmentList = departmentMapper.listDescendants(id, null);
        List<Long> idList = departmentList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        idList.add(id);
        return idList;
    }
}
