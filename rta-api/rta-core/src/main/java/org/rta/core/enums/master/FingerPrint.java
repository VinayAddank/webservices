package org.rta.core.enums.master;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * FingerPrint type having all(ten) types of fingers.</br>
 * Left Thumb,</br>
 * Left Index,</br>
 * Left Middle,</br>
 * Left Ring,</br>
 * Left Little,</br>
 * Right Thumb,</br>
 * Right Index,</br>
 * Right Middle,</br>
 * Right Ring,</br>
 * Right Little
 * 
 * @author rahul.sharma
 *
 */
public enum FingerPrint {

    // left hand
    LEFT_THUMB(11, "Left Thumb"), LEFT_INDEX(12, "Left Index"), LEFT_MIDDLE(13, "Left Middle"), LEFT_RING(14,
            "Left Ring"), LEFT_LITTLE(15, "Left Little"),

    // right hand
    RIGHT_THUMB(21, "Right Thumb"), RIGHT_INDEX(22, "Right Index"), RIGHT_MIDDLE(23, "Right Middle"), RIGHT_RING(24,
            "Right Ring"), RIGHT_LITTLE(25, "Right Little");

    private Integer value;
    private String label;

    private FingerPrint(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    private static final Integer FINGERS_COUNT = 10;
    private static Map<Integer, String> map;

    static {
        map = new LinkedHashMap<>(FINGERS_COUNT);
        for (FingerPrint fingerPrint : FingerPrint.values()) {
            map.put(fingerPrint.value, fingerPrint.label);
        }
    }

    public Integer getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    /**
     * Get FingerPrint from {@code label}
     * 
     * @param label
     * @return FingerPrint
     */
    public static FingerPrint getFingerPrint(String label) {
        if (!Objects.isNull(label)) {
            if (label.equals(LEFT_THUMB.label)) {
                return FingerPrint.LEFT_THUMB;
            }
            if (label.equals(LEFT_INDEX.label)) {
                return FingerPrint.LEFT_INDEX;
            }
            if (label.equals(LEFT_MIDDLE.label)) {
                return FingerPrint.LEFT_MIDDLE;
            }
            if (label.equals(LEFT_RING.label)) {
                return FingerPrint.LEFT_RING;
            }
            if (label.equals(LEFT_LITTLE.label)) {
                return FingerPrint.LEFT_LITTLE;
            }

            if (label.equals(RIGHT_THUMB.label)) {
                return FingerPrint.RIGHT_THUMB;
            }
            if (label.equals(RIGHT_INDEX.label)) {
                return FingerPrint.RIGHT_INDEX;
            }
            if (label.equals(RIGHT_MIDDLE.label)) {
                return FingerPrint.RIGHT_MIDDLE;
            }
            if (label.equals(RIGHT_RING.label)) {
                return FingerPrint.RIGHT_RING;
            }
            if (label.equals(RIGHT_LITTLE.label)) {
                return FingerPrint.RIGHT_LITTLE;
            }
        }
        return null;
    }

    /**
     * Get FingerPrint from {@code value}
     * 
     * @param value
     * @return FingerPrint
     */
    public static FingerPrint getFingerPrint(Integer value) {
        if (!Objects.isNull(value)) {
            if (value == LEFT_THUMB.value) {
                return LEFT_THUMB;
            }
            if (value == LEFT_INDEX.value) {
                return LEFT_INDEX;
            }
            if (value == LEFT_MIDDLE.value) {
                return LEFT_MIDDLE;
            }
            if (value == LEFT_RING.value) {
                return LEFT_RING;
            }
            if (value == LEFT_LITTLE.value) {
                return LEFT_LITTLE;
            }

            if (value == RIGHT_THUMB.value) {
                return RIGHT_THUMB;
            }
            if (value == RIGHT_INDEX.value) {
                return RIGHT_INDEX;
            }
            if (value == RIGHT_MIDDLE.value) {
                return RIGHT_MIDDLE;
            }
            if (value == RIGHT_RING.value) {
                return RIGHT_RING;
            }
            if (value == RIGHT_LITTLE.value) {
                return RIGHT_LITTLE;
            }
        }
        return null;
    }

    public static Map<Integer, String> getAll() {
        return map;
    }

}
