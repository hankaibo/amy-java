package cn.mypandora.springboot.modular.system.service;

import cn.mypandora.springboot.SpringbootApplicationTest;
import cn.mypandora.springboot.modular.system.model.po.Department;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DepartmentServiceTest extends SpringbootApplicationTest {

    @Autowired
    private DepartmentService departmentService;

    @Test
    public void testListAll() {
        List<Department> departmentList = departmentService.listUserDepartment(null, null);
        assertEquals(departmentList.size(), 4);
    }

    @Test
    public void testListChildren() {
        List<Department> departmentList = departmentService.listChildrenDepartment(1L, null);
        assertEquals(departmentList.size(), 2);
    }

    @Transactional(rollbackFor = Exception.class)
    @Test
    public void testAddDepartment() {
        Department department = new Department();
        department.setParentId(null);
        department.setStatus(1);
        department.setLft(1);
        department.setRgt(2);
        department.setLevel(1);
        department.setName("XX公司");
        boolean existParentId = departmentService.existParent(department);
        if (!existParentId) {
            departmentService.addDepartment(department);
            Department info = departmentService.getDepartmentById(department.getId());
            assertEquals(department.getName(), info.getName());
        }
    }

    @Test
    public void testGetDepartmentById() {
        Department department = departmentService.getDepartmentById(1L);
        assertEquals(department.getName(), "testAdd");
    }

    @Test
    public void testUpdateDepartment() {
        Department department = new Department();
        department.setName("testUpdate");
        department.setId(54L);
        departmentService.updateDepartment(department);
        Department info = departmentService.getDepartmentById(54L);
        assertEquals(info.getName(), "testUpdate");
    }

    @Test
    public void testEnableDepartment() {
    }

    @Test
    public void testDeleteDepartment() {
        departmentService.deleteDepartment(56L);
        int count = departmentService.listUserDepartment(null, null).size();
        assertEquals(count, 0);
    }

    @Test
    public void testMoveDepartment() {
        List<Department> departmentListBefore = departmentService.listUserDepartment(1L, null);
        System.out.println(departmentListBefore);
        departmentService.moveDepartment(31L, 53L);
        List<Department> departmentListAfter = departmentService.listUserDepartment(1L, null);
        System.out.println(departmentListAfter);
    }

    @Test
    public void testCountUserById() {
        int count = departmentService.countUserById(2L);
        assertEquals(count, 4);
    }

    @Test
    public void testIsExistParentId() {
    }

}