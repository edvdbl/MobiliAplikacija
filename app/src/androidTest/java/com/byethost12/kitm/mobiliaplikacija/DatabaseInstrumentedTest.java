package com.byethost12.kitm.mobiliaplikacija;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.List;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseInstrumentedTest {

    private Context instrumentationCtx;
    private DatabaseSQLite db;
    private List<Pokemonas> pokemonai;

    @Before
    public void setup() {
        instrumentationCtx = InstrumentationRegistry.getTargetContext();
        db = new DatabaseSQLite(instrumentationCtx);
        pokemonai = Collections.emptyList();
    }

    @Test
    public void testAddPokemon() {
        //public Pokemonas(String name, double weight,
        // double height, String cp, String abilities, String type) {
        Pokemonas pokemonas = new Pokemonas("Haunter", 123, 321, "Medium", "Vegan", "Water");
        db.addPokemon(pokemonas);
        pokemonai = db.getPokemonByName(pokemonas.getName());
        assertPokemonasEqual(pokemonas, pokemonai.get(0));
    }

    private void assertPokemonasEqual(Pokemonas expected, Pokemonas actual) {
        Assert.assertEquals(expected.getName(),actual.getName());
        Assert.assertEquals(expected.getWeight(),actual.getWeight());
        Assert.assertEquals(expected.getHeight(),actual.getHeight());
        Assert.assertEquals(expected.getCp(),actual.getCp());
        Assert.assertEquals(expected.getAbilities(),actual.getAbilities());
        Assert.assertEquals(expected.getType(),actual.getType());
    }

}