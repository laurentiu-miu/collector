package ro.home.collector;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import ro.home.collector.importer.UserSelector;
import ro.home.collector.model.UsersDto;

/**
 * Created by LaurentiuM on 01/10/2020.
 */
public class UtilityTest {
  @Test
  public void wasNotUpdatedTodayTrue(){
    assertTrue(
        UserSelector
            .wasNotUpdatedToday(UsersDto.builder().importDate(LocalDate.parse("2020-01-01")).build()));
  }
  @Test
  public void wasNotUpdatedTodayFalse(){
    assertFalse(UserSelector.wasNotUpdatedToday(UsersDto.builder().importDate(LocalDate.now()).build()));
  }
  @Test
  public void wasNotUpdatedTodayNull(){
    assertTrue(UserSelector.wasNotUpdatedToday(UsersDto.builder().build()));
  }
}
