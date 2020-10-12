package ro.home.collector;

import java.time.LocalDate;
import lombok.extern.log4j.Log4j2;
import ro.home.collector.model.UsersDto;

/**
 * Created by LaurentiuM on 30/09/2020.
 */
@Log4j2
public final class Utility {
  private Utility(){}
  public static boolean wasNotUpdatedToday(UsersDto user){
    log.info("User:{}",user.getUsername());
    return !LocalDate.now().equals(user.getImportDate());
  }
}
