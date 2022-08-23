package com.udacity.examples.Testing;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class HelperTest {

    @Test
    public void verify_getMergedList() {
        List<String> empName = Arrays.asList("Amy", "", "Alice", "", "Mery");
        String expectedNames = "Amy, Alice, Mery";
        String names = Helper.getMergedList(empName);
        Assert.assertEquals(expectedNames, names);
    }
	
}
