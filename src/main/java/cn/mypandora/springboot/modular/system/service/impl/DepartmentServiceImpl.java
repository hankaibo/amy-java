package cn.mypandora.springboot.modular.system.service.impl;

import cn.mypandora.springboot.modular.system.mapper.DepartmentMapper;
import cn.mypandora.springboot.modular.system.mapper.DepartmentUserMapper;
import cn.mypandora.springboot.modular.system.model.po.BaseEntity;
import cn.mypandora.springboot.modular.system.model.po.Department;
import cn.mypandora.springboot.modular.system.service.DepartmentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Map<String, Integer> map = new HashMap<>(1);
        map.put("status", status);
        return departmentMapper.listAll(map);
    }

    @Override
    public List<Department> listChildren(Long id, Integer status) {
        Map<String, Number> map = new HashMap<>(2);
        map.put("id", id);
        map.put("status", status);
        return departmentMapper.listChildren(map);
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
        Map<String, Number> map = new HashMap<>(2);
        map.put("id", department.getParentId());
        map.put("amount", 2);
        departmentMapper.lftAdd(map);
        departmentMapper.rgtAdd(map);
        departmentMapper.insert(department);
        departmentMapper.parentRgtAdd(map);
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
        Map<String, Object> map = new HashMap<>(2);
        map.put("idList", idList);
        map.put("status", status);
        departmentMapper.enableDescendants(map);
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
        Map<String, Number> map = new HashMap<>(2);
        map.put("id", id);
        map.put("amount", -deleteAmount);
        departmentMapper.lftAdd(map);
        departmentMapper.rgtAdd(map);
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
        Map<String, Object> sourceMap = new HashMap<>(2);
        sourceMap.put("idList", sourceIdList);
        sourceMap.put("amount", targetAmount);
        departmentMapper.selfAndDescendant(sourceMap);
        // 目标节点及子孙节点左右值 sourceAmount
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
     * 默认情况下，数据结构为单树(只有一条数据的parentId可为空)，数据库表初始化后有一条数据。
     * 为了避免没有初始化数据表就操作造成问题（比如多条数据的parentId为空），用代码避免这种情况。
     *
     * @param department 部门
     * @return 是否存在父部门，true表示存在，可以添加或者修改部门；反之，则false
     */
    @Override
    public boolean existParent(Department department) {
        // 如果parentId不为空，并存在于数据库表中，为真；
        if (department.getParentId() != null) {
            Department departmentParent = getDepartmentById(department.getParentId());
            return departmentParent != null;
        } else {
            // 否则parentId为空，必须保证是根部门修改或者首次创建（即数据库为空，添加部门时），为真。
            Department departmentRoot = getDepartmentById(department.getId());
            if (departmentRoot.getLevel() == 1) {
                return true;
            }
            int count = listAll(null).size();
            return count == 0;
        }
    }

    @Override
    public boolean isCanUpdateParent(Department department) {
        // 如果是根部门，parentId 为空，返回真。
        Department departmentRoot = getDepartmentById(department.getId());
        if (departmentRoot.getLevel() == 1) {
            return true;
        }
        // 如果不是根部门，进行父部门判断。
        Department childDepartment = getDepartmentById(department.getId());
        Department parentDepartment = getDepartmentById(department.getParentId());
        return parentDepartment.getLft() < childDepartment.getLft() || parentDepartment.getRgt() > childDepartment.getRgt();
    }

    /**
     * 获取此节点及其子孙节点的id
     *
     * @param id 节点
     * @return 节点集合
     */
    private List<Long> listDescendantId(Long id) {
        Map<String, Number> map = new HashMap<>(1);
        map.put("id", id);
        List<Department> departmentList = departmentMapper.listDescendants(map);
        List<Long> idList = departmentList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        idList.add(id);
        return idList;
    }

}
