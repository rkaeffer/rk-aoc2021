package fr.rk.aoc.challenge;

import fr.rk.aoc.challenge.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day16Test {

    String inputTest = "D2FE28";

    String inputTestVersionSumEx1 = "38006F45291200";
    String inputTestVersionSumEx2 = "EE00D40C823060";


    String inputTestVersionSum1 = "8A004A801A8002F478";
    String inputTestVersionSum2 = "620080001611562C8802118E34";
    String inputTestVersionSum3 = "C0015000016115A2E0802F182340";
    String inputTestVersionSum4 = "A0016C880162017C3686B18A3D4780";

    String inputTestOperation1 = "C200B40A82";
    String inputTestOperation2 = "04005AC33890";
    String inputTestOperation3 = "880086C3E88112";
    String inputTestOperation4 = "CE00C43D881120";
    String inputTestOperation5 = "D8005AC2A8F0";
    String inputTestOperation6 = "F600BC2D8F";
    String inputTestOperation7 = "9C005AC2F8F0";
    String inputTestOperation8 = "9C0141080250320F1802104A08";


    @Test
    public void testPacketVersionSum() {
        MatcherAssert.assertThat("", Day16.getSubpacketVersionSum(inputTestVersionSumEx1), Matchers.equalTo(9L));
        MatcherAssert.assertThat("", Day16.getSubpacketVersionSum(inputTestVersionSumEx2), Matchers.equalTo(14L));
        MatcherAssert.assertThat("", Day16.getSubpacketVersionSum(inputTestVersionSum1), Matchers.equalTo(16L));
        MatcherAssert.assertThat("", Day16.getSubpacketVersionSum(inputTestVersionSum2), Matchers.equalTo(12L));
        MatcherAssert.assertThat("", Day16.getSubpacketVersionSum(inputTestVersionSum3), Matchers.equalTo(23L));
        MatcherAssert.assertThat("", Day16.getSubpacketVersionSum(inputTestVersionSum4), Matchers.equalTo(31L));
    }

    @Test
    public void testPacketOperation() {
        MatcherAssert.assertThat("", Day16.getSubpacketOperationResult(inputTestOperation1), Matchers.equalTo(3L));
        MatcherAssert.assertThat("", Day16.getSubpacketOperationResult(inputTestOperation2), Matchers.equalTo(54L));
        MatcherAssert.assertThat("", Day16.getSubpacketOperationResult(inputTestOperation3), Matchers.equalTo(7L));
        MatcherAssert.assertThat("", Day16.getSubpacketOperationResult(inputTestOperation4), Matchers.equalTo(9L));
        MatcherAssert.assertThat("", Day16.getSubpacketOperationResult(inputTestOperation5), Matchers.equalTo(1L));
        MatcherAssert.assertThat("", Day16.getSubpacketOperationResult(inputTestOperation6), Matchers.equalTo(0L));
        MatcherAssert.assertThat("", Day16.getSubpacketOperationResult(inputTestOperation7), Matchers.equalTo(0L));
        MatcherAssert.assertThat("", Day16.getSubpacketOperationResult(inputTestOperation8), Matchers.equalTo(1L));
    }

    @Test
    public void getFirstChallengeResult() {
        FileUtils.readInputFileAsList("input.txt", 16)
                .ifPresent(lines -> log.info(String.valueOf(Day16.getSubpacketVersionSum(lines.get(0)))));
    }

    @Test
    public void getSecondChallengeResult() {
        FileUtils.readInputFileAsList("input.txt", 16)
                .ifPresent(lines -> log.info(String.valueOf(Day16.getSubpacketOperationResult(lines.get(0)))));
    }


}
