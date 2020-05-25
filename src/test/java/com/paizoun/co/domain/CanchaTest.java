package com.paizoun.co.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.paizoun.co.web.rest.TestUtil;

public class CanchaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cancha.class);
        Cancha cancha1 = new Cancha();
        cancha1.setId(1L);
        Cancha cancha2 = new Cancha();
        cancha2.setId(cancha1.getId());
        assertThat(cancha1).isEqualTo(cancha2);
        cancha2.setId(2L);
        assertThat(cancha1).isNotEqualTo(cancha2);
        cancha1.setId(null);
        assertThat(cancha1).isNotEqualTo(cancha2);
    }
}
