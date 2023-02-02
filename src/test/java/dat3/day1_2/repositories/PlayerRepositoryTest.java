package dat3.day1_2.repositories;

import dat3.day1_2.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;

    boolean dataIsInitalized = false;

    @BeforeEach
    public void setupPlayers() {
        if (!dataIsInitalized) {
            List<String> players = new ArrayList<>(Arrays.asList("aa", "bb", "cc"));
            List<Player> playerEnities = players.stream().map(p -> new Player(p)).toList();
            playerRepository.saveAll(playerEnities);
            dataIsInitalized = true;
        }
    }

    @Test
    public void testGetAll() {
        //Will this test pass, if it runs AFTER the next text
        List<Player> players = playerRepository.findAll();
        assertEquals(4, players.size());
    }

    @Test
    public void testAddPlayer() {
        Player player = playerRepository.save(new Player("dd"));
        //Q1: What's the difference between the player we add, and the player returned by the save method?
        //Q2: Write the test

        assertTrue(player.getId() > 0);
        long count = playerRepository.count();
        assertEquals(4, count);
    }

    @Test
    public void testFindPlayerByName() {
        // Q1: Write this test if you can. If not implement the missing parts
        Player player = playerRepository.findPlayerByName("bb");
        assertEquals("bb", player.getName());


        // Q2: Can You find player "dd" if this test runs after the previous test
        // no you can not. As per @DataJpaTest documentation, the environment rolls back after each test'
        // thus if we try to search for "dd" we will not get/find anything but null.
        Player player2 = playerRepository.findPlayerByName("dd");
        assertNull(player2);
    }
}
