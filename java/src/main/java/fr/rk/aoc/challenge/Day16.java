package fr.rk.aoc.challenge;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.ArrayList;

@Slf4j
public final class Day16 {

    /**
     * Calculate all packets versions sum
     *
     * @param packetAsHex Hexadecimal root packet representation
     *
     * @return Sum of versions of root packet and sub-packet
     */
    public static long getSubpacketVersionSum(String packetAsHex) {
        ParsedPacket p = parseOperatorPacket(hexToBin(packetAsHex));
        return getSumVersion(p.getPacket());
    }

    /**
     * Calculate root packet operations result
     *
     * @param packetAsHex Hexadecimal root packet representation
     *
     * @return root packet operations result
     */
    public static long getSubpacketOperationResult(String packetAsHex) {
        ParsedPacket p = parseOperatorPacket(hexToBin(packetAsHex));
        return getOperationResult(p.getPacket());
    }

    /**
     * Based on parsed root packet and sub-packet, calculate operation result
     *
     * @param rootPacket parsed root packet and sub-packet
     *
     * @return Operation result
     */
    private static long getOperationResult(Packet rootPacket) {
        if(rootPacket.getTypeId() == 4) {
            return rootPacket.value;
        } else if(rootPacket.getTypeId() == 0)  {
            long subTotal = 0;
            for(Packet p : rootPacket.getSubpacket()) {
                subTotal+=getOperationResult(p);
            }
            return subTotal;
        } else if(rootPacket.getTypeId() == 1)  {
            long subTotal = 1;
            for(Packet p : rootPacket.getSubpacket()) {
                subTotal*=getOperationResult(p);
            }
            return subTotal;
        } else if(rootPacket.getTypeId() == 2)  {
            long min = Long.MAX_VALUE;
            for(Packet p : rootPacket.getSubpacket()) {
                long val = getOperationResult(p);
                if(val < min) {
                    min = val;
                }
            }
            return min;
        } else if(rootPacket.getTypeId() == 3)  {
            long max = 0;
            for(Packet p : rootPacket.getSubpacket()) {
                long val = getOperationResult(p);
                if(val > max) {
                    max = val;
                }
            }
            return max;
        } else if(rootPacket.getTypeId() == 5)  {
            return getOperationResult(rootPacket.getSubpacket().get(0)) >  getOperationResult(rootPacket.getSubpacket().get(1)) ? 1L: 0L;
        } else if(rootPacket.getTypeId() == 6)  {
            return getOperationResult(rootPacket.getSubpacket().get(0)) < getOperationResult(rootPacket.getSubpacket().get(1)) ? 1L: 0L;
        } else if(rootPacket.getTypeId() == 7)  {
            return getOperationResult(rootPacket.getSubpacket().get(0)) == getOperationResult(rootPacket.getSubpacket().get(1)) ? 1L: 0L;
        }
        return 0L;
    }

    /**
     * Based on parsed root packet and sub-packet, calculate sum of all versions
     *
     * @param rootPacket parsed root packet and sub-packet
     *
     * @return sum of all versions
     */
    private static long getSumVersion(Packet rootPacket) {
        long sumVersion = rootPacket.getPacketVersion();
        for(Packet p : rootPacket.getSubpacket()) {
            sumVersion+=getSumVersion(p);
        }
        return sumVersion;
    }

    /**
     * Parse an operator packet
     *
     * @param packetAsBin binary representation of the packet
     *
     * @return parsed packet (As a packet and a trailing binary sequence non-parse)
     */
    private static ParsedPacket parseOperatorPacket(String packetAsBin) {
        int packetVersion = Integer.parseInt(packetAsBin.substring(0,3), 2);
        int typeId = Integer.parseInt(packetAsBin.substring(3,6), 2);
        Packet p = new Packet(packetVersion, typeId);
        p.setLengthId(Integer.parseInt(packetAsBin.substring(6,7), 2));
        int totalLength = 7;
        if(p.getLengthId() == 0) {
            totalLength+=15;
            int subPacketLength = Integer.parseInt(packetAsBin.substring(7,7+15), 2);
            String subpacket = packetAsBin.substring(totalLength, totalLength + subPacketLength);
            while(subpacket.length() != 0) {
                int subPacketTypeId = Integer.parseInt(subpacket.substring(3,6), 2);
                ParsedPacket parsedPacket;
                if(subPacketTypeId == 4) {
                    parsedPacket = parseValuePacket(subpacket);
                } else {
                    parsedPacket = parseOperatorPacket(subpacket);
                }
                p.getSubpacket().add(parsedPacket.getPacket());
                subpacket = parsedPacket.remainingString;
            }
            totalLength+=subPacketLength;
        } else {
            //Do Nothing for the moment
            totalLength+=11;
            int nbSubpacket = Integer.parseInt(packetAsBin.substring(7,7+11), 2);
            String subpacket = packetAsBin.substring(totalLength);
            int subPacketLength = subpacket.length();
            int subPacketTotalLength = 0;
            for(int i=0; i<nbSubpacket; i++) {
                int subPacketTypeId = Integer.parseInt(subpacket.substring(3,6), 2);
                ParsedPacket parsedPacket;
                if(subPacketTypeId == 4) {
                    parsedPacket = parseValuePacket(subpacket);
                } else {
                    parsedPacket = parseOperatorPacket(subpacket);
                }
                p.getSubpacket().add(parsedPacket.getPacket());
                subpacket = parsedPacket.remainingString;
                subPacketTotalLength = subPacketLength - subpacket.length();
            }
            totalLength+=subPacketTotalLength;
        }
        return new ParsedPacket(p, packetAsBin.substring(totalLength));
    }

    /**
     * Parse a value packet
     *
     * @param packetAsBin binary representation of the value packet
     *
     * @return parsed packet (As a packet and a trailing binary sequence non-parse)
     */
    private static ParsedPacket parseValuePacket(String packetAsBin) {
        int packetVersion = Integer.parseInt(packetAsBin.substring(0,3), 2);
        int typeId = Integer.parseInt(packetAsBin.substring(3,6), 2);
        Packet p = new Packet(packetVersion, typeId);
        int totalLength = 6;
        String[] packetContent = packetAsBin.substring(6).split("(?<=\\G.{5})");
        StringBuilder result = new StringBuilder();
        for(String val : packetContent) {
            totalLength += 5;
            result.append(val.substring(1));
            if(val.charAt(0) == '0') {
                break;
            }
        }
        p.setValue(Long.parseLong(result.toString(), 2));
        if(packetAsBin.substring(totalLength).replaceAll("0", "").length() == 0) {
            return new ParsedPacket(p, "");
        } else {
            return new ParsedPacket(p, packetAsBin.substring(totalLength));
        }
    }

    /**
     * Convert hexadecimal String to Binary String
     *
     * @param hex hexadecimal string
     *
     * @return binary string associated
     */
    private static String hexToBin(String hex){
        String bin = new String(hex);
        bin = bin.replaceAll("0", "0000");
        bin = bin.replaceAll("1", "0001");
        bin = bin.replaceAll("2", "0010");
        bin = bin.replaceAll("3", "0011");
        bin = bin.replaceAll("4", "0100");
        bin = bin.replaceAll("5", "0101");
        bin = bin.replaceAll("6", "0110");
        bin = bin.replaceAll("7", "0111");
        bin = bin.replaceAll("8", "1000");
        bin = bin.replaceAll("9", "1001");
        bin = bin.replaceAll("A", "1010");
        bin = bin.replaceAll("B", "1011");
        bin = bin.replaceAll("C", "1100");
        bin = bin.replaceAll("D", "1101");
        bin = bin.replaceAll("E", "1110");
        bin = bin.replaceAll("F", "1111");
        return bin;
    }


    /**
     * Class representing a packet
     */
    @Setter
    @Getter
    private static class Packet {
        private int packetVersion;
        private int typeId;
        private int lengthId;
        private long value;

        private String binary;

        private List<Packet> subpacket;

        public Packet(int packetVersion, int typeId) {
           this.packetVersion = packetVersion;
           this.typeId = typeId;
           this.lengthId = -1;
           this.subpacket = new ArrayList<>();
        }
    }

    /**
     * Class representing a parsed packet (As a packet and a trailing binary sequence non-parse)
     */
    @AllArgsConstructor
    @Getter
    private static class ParsedPacket {
        private Packet packet;
        String remainingString;
    }
}
