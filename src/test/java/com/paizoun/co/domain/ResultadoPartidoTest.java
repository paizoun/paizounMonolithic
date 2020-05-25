package com.paizoun.co.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.paizoun.co.web.rest.TestUtil;

public class ResultadoPartidoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResultadoPartido.class);
        ResultadoPartido resultadoPartido1 = new ResultadoPartido();
        resultadoPartido1.setId(1L);
        ResultadoPartido resultadoPartido2 = new ResultadoPartido();
        resultadoPartido2.setId(resultadoPartido1.getId());
        assertThat(resultadoPartido1).isEqualTo(resultadoPartido2);
        resultadoPartido2.setId(2L);
        assertThat(resultadoPartido1).isNotEqualTo(resultadoPartido2);
        resultadoPartido1.setId(null);
        assertThat(resultadoPartido1).isNotEqualTo(resultadoPartido2);
    }
}
