package y2011;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

public class FishTalesTest
{

    @Test
    public void testSolve_null()
    {
        final FishTales sut = new FishTales();
        try
        {
            sut.solve(null);
            fail("Expected an exception");
        }
        catch (final Exception e)
        {
            // expected
        }
    }

    @Test
    public void testSolve_empty()
    {
        final FishTales sut = new FishTales();
        try
        {
            sut.solve(new ArrayList<String>());
            fail("Expected an exception");
        }
        catch (final Exception e)
        {
            // expected
        }
    }

    @Test
    public void testSolve_good()
    {
        final List<String> input = new ArrayList<String>();
        input.add("ASOK: CAROLINE WAS THIRD");
        input.add("LACHELL: SAM BEAT CAROLINE");
        input.add("SAM: ASOK WAS LAST");
        input.add("CAROLINE: I BEAT LACHELL");

        final FishTales sut = new FishTales();
        final List<String> output = sut.solve(input);
        assertEquals("LACHELL", output.get(0));
        assertEquals("CAROLINE", output.get(1));
        assertEquals("ASOK", output.get(2));
        assertEquals("SAM", output.get(3));
    }

    @Test
    @Ignore("Add FOURTH to Position enum")
    public void testSolve_good5()
    {
        final List<String> input = new ArrayList<String>();
        input.add("NAVYA: I BEAT JING");
        input.add("JING: JAVIER BEAT NAVYA");
        input.add("JAVIER: NAVYA WAS SECOND");
        input.add("BRUCE: I BEAT HALLY");
        input.add("HALLY: JING BEAT BRUCE");

        final FishTales sut = new FishTales();
        final List<String> output = sut.solve(input);
        assertEquals("HALLY", output.get(0));
        assertEquals("BRUCE", output.get(1));
        assertEquals("JING", output.get(2));
        assertEquals("NAVYA", output.get(3));
        assertEquals("JAVIER", output.get(4));
    }

    @Test
    public void testSolve_tooBig()
    {
        final List<String> input = new ArrayList<String>();
        input.add("ASOK: CAROLINE WAS THIRD");
        input.add("LACHELL: SAM BEAT CAROLINE");
        input.add("SAM: ASOK WAS LAST");
        input.add("CAROLINE: I BEAT LACHELL");
        input.add("too: big");

        final FishTales sut = new FishTales();
        try
        {
            sut.solve(input);
            fail("Expected an exception");
        }
        catch (final Exception e)
        {
            // expected
        }
    }

    @Test
    public void testSolve_tooSmall()
    {
        final List<String> input = new ArrayList<String>();
        input.add("ASOK: CAROLINE WAS THIRD");
        input.add("LACHELL: SAM BEAT CAROLINE");
        input.add("SAM: ASOK WAS LAST");

        final FishTales sut = new FishTales();
        try
        {
            sut.solve(input);
            fail("Expected an exception");
        }
        catch (final Exception e)
        {
            // expected
        }
    }

    @Test
    public void testSolve_nameMismatch()
    {
        final List<String> input = new ArrayList<String>();
        input.add("ASOK: CAROLIN WAS THIRD");
        input.add("LACHELL: SAM BEAT CAROLINE");
        input.add("SAM: ASOK WAS LAST");
        input.add("CAROLINE: I BEAT LACHELL");

        final FishTales sut = new FishTales();
        try
        {
            sut.solve(input);
            fail("Expected an exception");
        }
        catch (final Exception e)
        {
            // expected
        }
    }

    @Test
    public void testSolve_positionMismatch()
    {
        final List<String> input = new ArrayList<String>();
        input.add("ASOK: CAROLINE WAS THIRD");
        input.add("LACHELL: SAM BEAT CAROLINE");
        input.add("SAM: ASOK WAS LOST");
        input.add("CAROLINE: I BEAT LACHELL");

        final FishTales sut = new FishTales();
        try
        {
            sut.solve(input);
            fail("Expected an exception");
        }
        catch (final Exception e)
        {
            // expected
        }
    }

    @Test
    public void testSolve_typeMismatch()
    {
        final List<String> input = new ArrayList<String>();
        input.add("ASOK: CAROLINE WAS THIRD");
        input.add("LACHELL: SAM BEAT1 CAROLINE");
        input.add("SAM: ASOK WAS LAST");
        input.add("CAROLINE: I BEAT LACHELL");

        final FishTales sut = new FishTales();
        try
        {
            sut.solve(input);
            fail("Expected an exception");
        }
        catch (final Exception e)
        {
            // expected
        }
    }

    @Test
    public void testSolve_missingColon()
    {
        final List<String> input = new ArrayList<String>();
        input.add("ASOK CAROLINE WAS THIRD");
        input.add("LACHELL: SAM BEAT CAROLINE");
        input.add("SAM: ASOK WAS LAST");
        input.add("CAROLINE: I BEAT LACHELL");

        final FishTales sut = new FishTales();
        try
        {
            sut.solve(input);
            fail("Expected an exception");
        }
        catch (final Exception e)
        {
            // expected
        }
    }

    @Test
    public void testSolve_missingName()
    {
        final List<String> input = new ArrayList<String>();
        input.add(": CAROLINE WAS THIRD");
        input.add("LACHELL: SAM BEAT CAROLINE");
        input.add("SAM: ASOK WAS LAST");
        input.add("CAROLINE: I BEAT LACHELL");

        final FishTales sut = new FishTales();
        try
        {
            sut.solve(input);
            fail("Expected an exception");
        }
        catch (final Exception e)
        {
            // expected
        }
    }

    @Test
    public void testSolve_missingClue()
    {
        final List<String> input = new ArrayList<String>();
        input.add("ASOK:");
        input.add("LACHELL: SAM BEAT CAROLINE");
        input.add("SAM: ASOK WAS LAST");
        input.add("CAROLINE: I BEAT LACHELL");

        final FishTales sut = new FishTales();
        try
        {
            sut.solve(input);
            fail("Expected an exception");
        }
        catch (final Exception e)
        {
            // expected
        }
    }

    @Test
    public void testSolve_incompleteClue()
    {
        final List<String> input = new ArrayList<String>();
        input.add("ASOK: WAS THIRD");
        input.add("LACHELL: SAM BEAT CAROLINE");
        input.add("SAM: ASOK WAS LAST");
        input.add("CAROLINE: I BEAT LACHELL");

        final FishTales sut = new FishTales();
        try
        {
            sut.solve(input);
            fail("Expected an exception");
        }
        catch (final Exception e)
        {
            // expected
        }
    }

    @Test
    public void testTruthTable_init()
    {
        final List<String> names = Arrays.asList(new String[] { "a", "b" });
        final FishTales.TruthTable table = new FishTales.TruthTable(names);

        final Collection<FishTales.TruthCell> cells = table.getAllCells();
        for (final FishTales.TruthCell cell : cells)
        {
            assertTrue(cell.isUnknown());
        }
    }

    @Test
    public void testTruthTable_modifyFalse()
    {
        final List<String> names = Arrays.asList(new String[] { "a", "b", "c" });
        final FishTales.TruthTable table = new FishTales.TruthTable(names);

        table.setFalse("b", 1);

        assertEquals(FishTales.Truth.False, table.getTruthCellsByName("b").get(1).getValue());
        assertEquals(FishTales.Truth.Unknown, table.getTruthCellsByName("b").get(0).getValue());
        assertEquals(FishTales.Truth.Unknown, table.getTruthCellsByName("b").get(2).getValue());
        assertEquals(FishTales.Truth.False, table.getTruthCellsByPosition(1).get(1).getValue());
        assertEquals(FishTales.Truth.Unknown, table.getTruthCellsByPosition(1).get(0).getValue());
        assertEquals(FishTales.Truth.Unknown, table.getTruthCellsByPosition(1).get(2).getValue());
    }

    @Test
    public void testTruthTable_modifyTrue()
    {
        final List<String> names = Arrays.asList(new String[] { "a", "b", "c" });
        final FishTales.TruthTable table = new FishTales.TruthTable(names);

        table.setTrue("b", 1);

        assertEquals(FishTales.Truth.True, table.getTruthCellsByName("b").get(1).getValue());
        assertEquals(FishTales.Truth.False, table.getTruthCellsByName("b").get(0).getValue());
        assertEquals(FishTales.Truth.False, table.getTruthCellsByName("b").get(2).getValue());
        assertEquals(FishTales.Truth.True, table.getTruthCellsByPosition(1).get(1).getValue());
        assertEquals(FishTales.Truth.False, table.getTruthCellsByPosition(1).get(0).getValue());
        assertEquals(FishTales.Truth.False, table.getTruthCellsByPosition(1).get(2).getValue());
    }

    @Test
    public void testSolveTable()
    {
        final List<String> names = Arrays.asList(new String[] { "a", "b" });
        final FishTales.TruthTable table = new FishTales.TruthTable(names);
        table.setFalse("b", 1);

        final List<String> soln = table.solve(new ArrayList<FishTales.Clue>());

        assertEquals("b", soln.get(0));
        assertEquals("a", soln.get(1));
    }

    @Test
    public void testConvertRelativeToAbsoluteReferences()
    {
        assertEquals("Abe was first", FishTales.convertRelativeToAbsoluteReferences("Abe", "I was first"));
    }

    @Test
    public void testExtractBestPosition()
    {
        final List<FishTales.TruthCell> positions = new ArrayList<FishTales.TruthCell>();
        positions.add(new FishTales.TruthCell("", 0, FishTales.Truth.False));
        positions.add(new FishTales.TruthCell("", 1, FishTales.Truth.Unknown));
        final int actual = FishTales.TruthTable.extractBestPossiblePosition(positions);
        assertEquals(1, actual);
    }

    @Test
    public void testExtractWorstPosition()
    {
        final List<FishTales.TruthCell> positions = new ArrayList<FishTales.TruthCell>();
        positions.add(new FishTales.TruthCell("", 0, FishTales.Truth.False));
        positions.add(new FishTales.TruthCell("", 1, FishTales.Truth.Unknown));
        positions.add(new FishTales.TruthCell("", 2, FishTales.Truth.False));
        final int actual = FishTales.TruthTable.extractWorstPossiblePosition(positions);
        assertEquals(1, actual);
    }

    @Test
    public void testExtractHardClues()
    {
        final List<String> names = Arrays.asList(new String[] { "a", "b", "c" });
        final FishTales.TruthTable table = new FishTales.TruthTable(names);
        table.setFalse("b", 2);
        table.setFalse("b", 0);
        table.setFalse("c", 2);

        final Collection<FishTales.Clue> clues = new ArrayList<FishTales.Clue>();
        clues.add(new FishTales.ClueBeat("b", "c"));
        final Collection<FishTales.TruthCell> truths = table.extractHardClues(clues);
        assertEquals(2, truths.size());
        final Iterator<FishTales.TruthCell> iter = truths.iterator();
        final FishTales.TruthCell truth1 = iter.next();
        assertEquals("c", truth1.getName());
        assertEquals(1, truth1.getPosition());
        assertEquals(FishTales.Truth.False, truth1.getValue());
        final FishTales.TruthCell truth2 = iter.next();
        assertEquals("c", truth2.getName());
        assertEquals(2, truth2.getPosition());
        assertEquals(FishTales.Truth.False, truth2.getValue());
    }
}
