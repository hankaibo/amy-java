package cn.mypandora.springboot.modular.system.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.mypandora.springboot.SpringbootApplicationTest;
import cn.mypandora.springboot.modular.system.model.po.Dictionary;

public class DictionaryServiceTest extends SpringbootApplicationTest {

    @Autowired
    private DictionaryService dictionaryService;

    public void testPageDictionary() {}

    @Test
    public void testAddDictionary() {
        Dictionary dictionary = new Dictionary();
        dictionary.setName("testName");
        dictionary.setStatus(1);
        dictionary.setValue("man");
        dictionary.setCode("man");
        dictionaryService.addDictionary(dictionary);

        Dictionary dictionary2 = new Dictionary();
        dictionary2.setName("testNam2e");
        dictionary2.setStatus(1);
        dictionary2.setValue("man");
        dictionary2.setCode("man");
        dictionary2.setParentId(dictionary.getId());
        dictionaryService.addDictionary(dictionary2);

        Dictionary dictionary3 = new Dictionary();
        dictionary3.setName("testNam2e");
        dictionary3.setStatus(1);
        dictionary3.setValue("man");
        dictionary3.setCode("man");
        dictionary3.setParentId(dictionary2.getId());
        dictionaryService.addDictionary(dictionary3);
    }

    public void testGetDictionary() {}

    public void testUpdateDictionary() {}

    public void testEnableDictionary() {}

    @Test
    public void testDeleteDictionary() {
        dictionaryService.deleteDictionary(1L);
        long total = dictionaryService.pageDictionary(1, 100, null).getTotal();
        assertEquals(total, 0);
    }

    public void testDeleteBatchDictionary() {}
}