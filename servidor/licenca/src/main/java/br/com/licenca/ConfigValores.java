package br.com.licenca;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class ConfigValores {
    private final List<String> codigoLicencasExistentes = Arrays.asList(
            "15eab2af72303654c3351dc97a1c5ba1",
            "6d8f81c6fcae3c180c46e9c13d4191ad",
            "b28026edefbc10aa7b46ffee8fd1846e03e79133",
            "6f04aa8324068801354b01b63f16f331",
            "A6C618DF976E4D48FFEE34DEE90109C2"
        );
    private final List<Integer> idUsuariosExistentes = Arrays.asList(
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29,
            30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56,
            57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83,
            84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100
        );
}
