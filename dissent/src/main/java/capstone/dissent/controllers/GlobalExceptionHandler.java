//package capstone.dissent.controllers;
//
//import org.springframework.dao.DataAccessException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice // TODO we may want to log exceptions if time allows
//public class GlobalExceptionHandler {
//
//    // DataAccessException is the super class of many Spring database exceptions
//    // including BadSqlGrammarException.
//    @ExceptionHandler(DataAccessException.class)
//    public ResponseEntity<ErrorResponse> handleException(DataAccessException ex) {
//        return new ResponseEntity<ErrorResponse>(
//                new ErrorResponse("We can't show you the details, but something went wrong in our database. Sorry :("),
//                HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<ErrorResponse> handleException(IllegalArgumentException ex) {
//        return new ResponseEntity<ErrorResponse>(
//                new ErrorResponse(ex.getMessage()),
//                HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
//        return new ResponseEntity<ErrorResponse>(
//                new ErrorResponse("Something went wrong on our end. Your request failed. :("),
//                HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}