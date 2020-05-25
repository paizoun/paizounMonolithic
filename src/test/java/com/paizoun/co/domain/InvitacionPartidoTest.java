package com.paizoun.co.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.paizoun.co.web.rest.TestUtil;

public class InvitacionPartidoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvitacionPartido.class);
        InvitacionPartido invitacionPartido1 = new InvitacionPartido();
        invitacionPartido1.setId(1L);
        InvitacionPartido invitacionPartido2 = new InvitacionPartido();
        invitacionPartido2.setId(invitacionPartido1.getId());
        assertThat(invitacionPartido1).isEqualTo(invitacionPartido2);
        invitacionPartido2.setId(2L);
        assertThat(invitacionPartido1).isNotEqualTo(invitacionPartido2);
        invitacionPartido1.setId(null);
        assertThat(invitacionPartido1).isNotEqualTo(invitacionPartido2);
    }
}
