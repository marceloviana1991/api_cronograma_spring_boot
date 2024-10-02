package cronograma.api.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler({EntityNotFoundException.class, NullPointerException.class})
    public ResponseEntity<?> tratarErro404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> tratarErro400(MethodArgumentNotValidException methodArgumentNotValidException) {
        var erros = methodArgumentNotValidException.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new));
    }

    @ExceptionHandler({ValidacaoException.class})
    public ResponseEntity<?> tratarErro400(RuntimeException runtimeException) {
        return ResponseEntity.badRequest().body(new DadosErro(runtimeException));
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<?> tratarErro400() {
        return ResponseEntity.badRequest().body(new DadosErroValicacaoGenerico());
    }

    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }

    private record DadosErro(String mensagem) {
        public DadosErro(RuntimeException erro) {
            this(erro.getMessage());
        }
    }
    private record DadosErroValicacaoGenerico(String mensagem) {
        public DadosErroValicacaoGenerico() {
            this("Campo inválido");
        }
    }

}
