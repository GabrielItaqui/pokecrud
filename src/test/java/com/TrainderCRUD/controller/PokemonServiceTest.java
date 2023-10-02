//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//import com.TrainerCRUD.service.PokemonService;
//import com.TrainerCRUD.model.Pokemon;
//
//import org.junit.jupiter.api.Test;
//
//public class PokemonServiceTest {
//
//  @Test
//  public void testGetPokemonPorID_Sucesso() throws Exception {
//    // Arrange
//    int id = 1;
//    String json = "{ \"id\": 1, \"name\": \"Bulbasaur\" }";
//
//    // Act
//    Pokemon pokemon = PokemonService.getPokemonPorID(id);
//
//    // Assert
//    assertEquals(1, pokemon.getId());
//    assertEquals("Bulbasaur", pokemon.getName());
//  }
//
//  @Test
//  public void testGetPokemonPorID_Erro() throws Exception {
//    // Arrange
//    int id = 1;
//
//    // Act
//    Exception exception = assertThrows(Exception.class, () -> PokemonService.getPokemonPorID(id));
//
//    // Assert
//    assertEquals("Erro ao consumir a PokéAPI", exception.getMessage());
//  }
//};