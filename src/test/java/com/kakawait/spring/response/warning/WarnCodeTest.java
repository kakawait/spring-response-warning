package com.kakawait.spring.response.warning;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Thibaud Leprêtre
 */
public class WarnCodeTest {

    @Test
    public void warnCode_GetCode_ExpectedIntegerCode() {
        assertThat(WarnCode.RESPONSE_IS_STALE.getCode()).isEqualTo(110);
        assertThat(WarnCode.REVALIDATION_FAILED.getCode()).isEqualTo(111);
        assertThat(WarnCode.DISCONNECTED_OPERATION.getCode()).isEqualTo(112);
        assertThat(WarnCode.HEURISTIC_EXPIRATION.getCode()).isEqualTo(113);
        assertThat(WarnCode.MISCELLANEOUS_WARNING.getCode()).isEqualTo(199);

        assertThat(WarnCode.TRANSFORMATION_APPLIED.getCode()).isEqualTo(214);
        assertThat(WarnCode.MISCELLANEOUS_PERSISTENT_WARNING.getCode()).isEqualTo(299);
    }

    @Test
    public void warnCode_GetFamily_ExpectedHundred() {
        assertThat(WarnCode.RESPONSE_IS_STALE.getFamily()).isEqualTo(1);
        assertThat(WarnCode.REVALIDATION_FAILED.getFamily()).isEqualTo(1);
        assertThat(WarnCode.DISCONNECTED_OPERATION.getFamily()).isEqualTo(1);
        assertThat(WarnCode.HEURISTIC_EXPIRATION.getFamily()).isEqualTo(1);
        assertThat(WarnCode.MISCELLANEOUS_WARNING.getFamily()).isEqualTo(1);

        assertThat(WarnCode.TRANSFORMATION_APPLIED.getFamily()).isEqualTo(2);
        assertThat(WarnCode.MISCELLANEOUS_PERSISTENT_WARNING.getFamily()).isEqualTo(2);
    }
}
