package com.paizoun.co.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.paizoun.co.web.rest.TestUtil;

public class EstadoPartidoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstadoPartido.class);
        EstadoPartido estadoPartido1 = new EstadoPartido();
        estadoPartido1.setId(1L);
        EstadoPartido estadoPartido2 = new EstadoPartido();
        estadoPartido2.setId(estadoPartido1.getId());
        assertThat(estadoPartido1).isEqualTo(estadoPartido2);
        estadoPartido2.setId(2L);
        assertThat(estadoPartido1).isNotEqualTo(estadoPartido2);
        estadoPartido1.setId(null);
        assertThat(estadoPartido1).isNotEqualTo(estadoPartido2);
    }
}
