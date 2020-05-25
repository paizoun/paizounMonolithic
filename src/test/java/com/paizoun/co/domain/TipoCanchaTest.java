package com.paizoun.co.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.paizoun.co.web.rest.TestUtil;

public class TipoCanchaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoCancha.class);
        TipoCancha tipoCancha1 = new TipoCancha();
        tipoCancha1.setId(1L);
        TipoCancha tipoCancha2 = new TipoCancha();
        tipoCancha2.setId(tipoCancha1.getId());
        assertThat(tipoCancha1).isEqualTo(tipoCancha2);
        tipoCancha2.setId(2L);
        assertThat(tipoCancha1).isNotEqualTo(tipoCancha2);
        tipoCancha1.setId(null);
        assertThat(tipoCancha1).isNotEqualTo(tipoCancha2);
    }
}
