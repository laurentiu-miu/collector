package ro.home.collector;

import java.time.LocalDate;
import ro.home.collector.model.UsersDto;

/**
 * Created by LaurentiuM on 30/09/2020.
 */
public class Utility {
  public static boolean wasNotUpdatedToday(UsersDto user){
    return !LocalDate.now().equals(user.getImportDate());
  }
}
