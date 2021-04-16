/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.service;

import app.entity.OffreDeTravail;
import java.util.List;
import javafx.stage.Stage;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Anis
 */
public class OffreDeTravailCrudNGTest {
    
    public OffreDeTravailCrudNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of ajouterOffre method, of class OffreDeTravailCrud.
     */
    @Test
    public void testAjouterOffre() {
        System.out.println("ajouterOffre");
        OffreDeTravail o = null;
        OffreDeTravailCrud instance = new OffreDeTravailCrud();
        instance.ajouterOffre(o);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of SupprimerOffre method, of class OffreDeTravailCrud.
     */
    @Test
    public void testSupprimerOffre() {
        System.out.println("SupprimerOffre");
        int id = 0;
        OffreDeTravailCrud instance = new OffreDeTravailCrud();
        instance.SupprimerOffre(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ModififerOffre method, of class OffreDeTravailCrud.
     */
    @Test
    public void testModififerOffre() {
        System.out.println("ModififerOffre");
        OffreDeTravail o = null;
        int id = 0;
        OffreDeTravailCrud instance = new OffreDeTravailCrud();
        instance.ModifierOffre(o);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of DisplayOffre method, of class OffreDeTravailCrud.
     */
    @Test
    public void testDisplayOffre() {
        System.out.println("DisplayOffre");
        OffreDeTravailCrud instance = new OffreDeTravailCrud();
        List expResult = null;
        List result = instance.DisplayOffre();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of start method, of class OffreDeTravailCrud.
     */
    @Test
    public void testStart() throws Exception {
        System.out.println("start");
        Stage stage = null;
        OffreDeTravailCrud instance = new OffreDeTravailCrud();
        instance.start(stage);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
