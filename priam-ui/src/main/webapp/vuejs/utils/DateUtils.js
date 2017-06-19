/**
 * Created by benmerzoukah on 19/06/2017.
 */

const stringToDateZeroHour = (str) => {
    if (typeof str === 'string' && str.length > 3 && str.length < 11) {
      var parts = str.split("/");
      return new Date(Date.UTC(parts[2], parts[1] - 1, parts[0], 0, 0, 0, 0));
    }
    return null;
}

const stringToDate24Hour = (str) => {
  if (typeof str === 'string' && str.length > 3 && str.length < 11) {
    var parts = str.split("/");
    return new Date(Date.UTC(parts[2], parts[1] - 1, parts[0], 23, 59, 59, 999));
  }
  return null;
}
export const DateUtils = {
  stringToDateZeroHour, stringToDate24Hour
}
