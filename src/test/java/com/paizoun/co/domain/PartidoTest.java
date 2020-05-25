package com.paizoun.co.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.paizoun.co.web.rest.TestUtil;

public class PartidoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Partido.class);
        Partido partido1 = new Partido();
        partido1.setId(1L);
        Partido partido2 = new Partido();
        partido2.setId(partido1.getId());
        assertThat(partido1).isEqualTo(partido2);
        partido2.setId(2L);
        assertThat(partido1).isNotEqualTo(partido2);
        partido1.setId(null);
        assertThat(partido1).isNotEqualTo(partido2);
    }
}
