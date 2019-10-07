package cn.mypandora.springboot.modular.system.service;

import cn.mypandora.springboot.SpringbootApplicationTest;
import cn.mypandora.springboot.modular.system.model.po.Department;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

public class DepartmentServiceTest extends SpringbootApplicationTest {

    @Autowired
    private DepartmentService departmentService;

    @Test
    public void testListAll() {
        List<Department> departmentList = departmentService.listAll();
        assertEquals(departmentList.size(), 4);
    }

    @Test
    public void testListDescendants() {
        List<Department> departmentList = departmentService.listDescendants(2L);
        assertEquals(departmentList.size(), 3);
    }

    @Test
    public void testListChildren() {
        List<Department> departmentList = departmentService.listChildren(2L);
        assertEquals(departmentList.size(), 2);
    }

    @Test
    public void testGetParent() {
        Department department = departmentService.getParent(2L);
        assertNull(department);
    }

    @Test
    public void testListAncestries() {
        List<Department> departmentList = departmentService.listAncestries(54L);
        assertEquals(departmentList.size(), 2L);
    }

    @Test
    public void testListSiblings() {
        List<Department> departmentList = departmentService.listSiblings(31L);
        assertEquals(departmentList.size(), 2);
    }

    //    @Transactional(rollbackFor = Exception.class)
    @Test
    public void testAddDepartment() {
        Department department = new Department();
        department.setParentId(null);
        department.setStatus(1);
        department.setLft(1);
        department.setRgt(2);
        department.setLevel(1);
        department.setName("XX公司");
        departmentService.addDepartment(department);
        Department info = departmentService.getDepartmentById(department.getId());
        assertEquals(department.getName(), info.getName());
    }

    @Test
    public void testDeleteDepartment() {
        departmentService.deleteDepartment(56L);
        int count = departmentService.listAll().size();
        assertEquals(count, 0);
    }

    @Test
    public void testMoveDepartment() {
        List<Department> departmentListBefore = departmentService.listAll();
        System.out.println(departmentListBefore);
        departmentService.moveDepartment(31L, 53L);
        List<Department> departmentListAfter = departmentService.listAll();
        System.out.println(departmentListAfter);

    }

    @Test
    public void testGetDepartmentById() {
        Department department = departmentService.getDepartmentById(56L);
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
    public void testCountUserById() {
        int count = departmentService.countUserById(2L);
        assertEquals(count, 4);
    }
}