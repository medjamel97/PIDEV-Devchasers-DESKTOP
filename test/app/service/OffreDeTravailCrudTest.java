/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.service;

import app.entity.OffreDeTravail;
import java.util.List;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Anis
 */
public class OffreDeTravailCrudTest {
    
    public OffreDeTravailCrudTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
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
     * Test of supprimerProduit method, of class OffreDeTravailCrud.
     */
    @Test
    public void SupprimerOffre() {
        System.out.println("supprimerProduit");
        int id = 0;
        OffreDeTravailCrud instance = new OffreDeTravailCrud();
        instance.SupprimerOffre(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateProduit method, of class OffreDeTravailCrud.
     */
    @Test
    public void ModifierOffre() {
        System.out.println("updateProduit");
        OffreDeTravail o = null;
        OffreDeTravailCrud instance = new OffreDeTravailCrud();
        instance.ModifierOffre(o);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of displayOffre method, of class OffreDeTravailCrud.
     */
    @Test
    public void DisplayOffre() {
        System.out.println("displayOffre");
        OffreDeTravailCrud instance = new OffreDeTravailCrud();
        List<OffreDeTravail> expResult = null;
        List<OffreDeTravail> result = instance.displayOffre();
        assertEquals(expResult, result);
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
