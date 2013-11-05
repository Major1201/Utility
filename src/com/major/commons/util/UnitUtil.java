package com.major.commons.util;

/**
 * User: Minjie
 * Date: 13-9-20
 * Time: 上午12:28
 */
@SuppressWarnings("UnusedDeclaration")
public class UnitUtil {
    /**
     * Don't let anyone instantiate this class.
     */
    private UnitUtil() {}

    /**
     * enumeration of length unit
     */
    public static enum Length {
        SI(1), //国标
        LIGHT_YEAR(9.4605284e15), //光年
        ASTRONOMICAL_UNIT(1.4959800e11), //天文单位
        KILOMETER(1e3), METER(1), DELIMITER(0.1), CENTIMETER(0.01), MILLIMETER(0.001), MICRON(1e-6), //公制

        NAUTICAL_MILE(1852), //海里
        MILE(1609.344), //英里
        YARD(0.9144), //码
        FEET(0.3048), //英尺
        INCH(0.0254), //英寸

        LI(500), ZHANG(10D / 3), CHI(ZHANG.value / 10), CUN(CHI.value / 10),
        FEN(CUN.value / 10), CLI(FEN.value / 10), HAO(CLI.value / 10); //市制

        private double value;

        private Length(double value) {
            this.value = value;
        }
    }

    /**
     * enumeration of area unit
     */
    public static enum Area {
        SI(1),
        SQUARE_KILOMETER(1e6),
        HA(1e4), //公顷
        ARE(1e2), //公亩
        SQUARE_METER(1), SQUARE_DELIMETER(1e-2), SQUARE_CENTIMETER(1e-4), SQUARE_MILLIMETER(1e-6),

        SQUARE_MILE(2589988.11033), ACRE(4046.8564224), //英亩
        SQUARE_POLE(25.29285264), //平方竿
        SQUARE_YARD(0.83612736), SQUARE_FEET(0.09290304), SQUARE_INCH(0.00064516),

        QIN(200000D / 3), MU(2000D / 3), SQUARE_CHI(1D / 9), SQUARE_CUN(1D / 900);

        private double value;

        private Area(double value) {
            this.value = value;
        }
    }

    /**
     * enumeration of volume unit
     */
    public static enum Volume {
        SI(1),
        CUBIC_METER(1), LITER(1e-3), CUBIC_DELIMETER(1e-3), DELILITER(1e-4), CENTILITER(1e-5), MILLILITER(1e-6), CUBIC_CENTIMETER(1e-6), CUBIC_MILLIMETER(1e-9),

        CUBIC_YARD(0.7645536), CUBIC_FEET(0.0283168), CUBIC_INCH(16.387037037 * MILLILITER.value),
        UK_GALLON(4.54609188 * LITER.value), US_GALLON(3.785411784 * LITER.value), //加仑
        BARREL(0.159), CUP(0.23658824 * LITER.value), FLUID_OZ(0.125 * CUP.value), TEASPOON(14.786765 * MILLILITER.value); //桶，杯，液盎司，茶匙

        private double value;

        private Volume(double value) {
            this.value = value;
        }
    }

    /**
     * enumeration of weight unit
     */
    public static enum Weight {
        SI(1),
        TON(1e-3), QUINTAL(1e-2), KILOGRAM(1), GRAM(1e3), MILLIGRAM(1e6), Microgram(1e9),

        POUND(0.45359237), OZ(28.349523125 * GRAM.value), CARAT(0.2 * GRAM.value), //克拉

        DAN(50), JIN(0.5), LIANG(0.05), QIAN(0.005);

        private double value;

        private Weight(double value) {
            this.value = value;
        }
    }

    /**
     * enumeration of temperature unit
     */
    public static enum Temperature {
        SI,
        KELVIN, CENTIGREDE, FAHRENHEIT
    }

    /**
     * enumeration of pressure unit
     */
    public static enum Pressure {
        SI(1),
        PASCAL(1), KILOPascal(1e3), METAPASCAL(1e6), ATM(101325), mmHg(133.322368421);

        private double value;

        private Pressure(double value) {
            this.value = value;
        }
    }

    /**
     * enumeration of power unit
     */
    public static enum Power {
        SI(1),
        WATT(1), KILOWATT(1e3), METAWATT(1e6), MILLIWATT(1e-3),
        BHP(745.69987158), //英制马力
        MHP(735.49875); //米制马力

        private double value;

        private Power(double value) {
            this.value = value;
        }
    }

    /**
     * enumeration of energy unit
     */
    public static enum Energy {
        SI(1),
        JOULE(1), KILOJOULE(1e3), CALORY(4.184), KILOCALORY(4184), KWH(3.6e6);

        private double value;

        private Energy(double value) {
            this.value = value;
        }
    }

    /**
     * enumeration of force unit
     */
    public static enum Force {
        SI(1),
        NEWTON(1), KP(9.80665); //千克力

        private double value;

        private Force(double value) {
            this.value = value;
        }
    }

    /**
     * enumeration of time unit
     */
    public static enum Time {
        SI(1),
        SECOND(1), MINUTE(60), HOUR(3600), DAY(3600 * 24), WEEK(3600 * 24 * 7),
        MILLISECOND(1e-3), MICROSECOND(1e-6), NANOSECOND(1e-9), PICOSECOND(1e-12);

        private double value;

        private Time(double value) {
            this.value = value;
        }
    }

    /**
     * enumeration of speed unit
     */
    public static enum Speed {
        SI(1),
        METER_PER_SECOND(1), KILOMETER_PER_HOUR(1D / 3.6), MACH(340.3), //马赫
        MILE_PER_HOUR(0.44704), C(33356409.29729), KNOT(0.514444444444); //海里/时

        private double value;

        private Speed(double value) {
            this.value = value;
        }
    }

    /**
     * enumeration of angle unit
     */
    public static enum Angle {
        SI(1),
        DEGREE(1), MINUTE(1D / 60), SECOND(1D / 3600), CIRCLE(1D / 180), RAD(180D / Math.PI);

        private double value;

        private Angle(double value) {
            this.value = value;
        }
    }

    /**
     * enumeration of storage unit
     */
    public static enum Storage {
        SI(1),

        BYTE(1), KILOBYTE(1000), MEGABYTE(Math.pow(1000, 2)), GIGABYTE(Math.pow(1000, 3)), TERABYTE(Math.pow(1000, 4)),
        PETABYTE(Math.pow(1000, 5)), EXABYTE(Math.pow(1000, 6)), ZETTABYTE(Math.pow(1000, 7)), YOTTABYTE(Math.pow(1000, 8)),

        KIBIBYTE(1024), MEBIBYTE(Math.pow(1024, 2)), GIBIBYTE(Math.pow(1024, 3)), TEBIBYTE(Math.pow(1024, 4)),
        PEBIBYTE(Math.pow(1024, 5)), EXBIBYTE(Math.pow(1024, 6)), ZEBIBYTE(Math.pow(1024, 7)), YOBIBYTE(Math.pow(1024, 8)),

        BIT(0.125), KILOBIT(0.125 * 1000), MEGABIT(0.125 * Math.pow(1000, 2)), GIGABIT(0.125 * Math.pow(1000, 3)), TERABIT(0.125 * Math.pow(1000, 4)),
        PETABIT(0.125 * Math.pow(1000, 5)), EXABIT(0.125 * Math.pow(1000, 6)), ZETTABIT(0.125 * Math.pow(1000, 7)), YOTTABIT(0.125 * Math.pow(1000, 8)),

        KIBIBIT(0.125 * 1024), MEBIBIT(0.125 * Math.pow(1024, 2)), GIBIBIT(0.125 * Math.pow(1024, 3)), TEBIBIT(0.125 * Math.pow(1024, 4)),
        PEBIBIT(0.125 * Math.pow(1024, 5)), EXBIBIT(0.125 * Math.pow(1024, 6)), ZEBIBIT(0.125 * Math.pow(1024, 7)), YOBIBIT(0.125 * Math.pow(1024, 8));

        private double value;

        private Storage(double value) {
            this.value = value;
        }
    }

    /**
     * Conversion unit of length
     * @param value value to convert
     * @param from unit
     * @param to unit
     * @return result
     */
    public static double lengthConverter(double value, Length from, Length to) {
        return value * from.value / to.value;
    }

    /**
     * Conversion unit of area
     * @param value value to convert
     * @param from unit
     * @param to unit
     * @return result
     */
    public static double areaConverter(double value, Area from, Area to) {
        return value * from.value / to.value;
    }

    /**
     * Conversion unit of volume
     * @param value value to convert
     * @param from unit
     * @param to unit
     * @return result
     */
    public static double volumeConverter(double value, Volume from, Volume to) {
        return value * from.value / to.value;
    }

    /**
     * Conversion unit of weight
     * @param value value to convert
     * @param from unit
     * @param to unit
     * @return result
     */
    public static double weightConverter(double value, Weight from, Weight to) {
        return value * from.value / to.value;
    }

    /**
     * Conversion unit of temperature
     * @param value value to convert
     * @param from unit
     * @param to unit
     * @return result
     */
    public static double temperatureConverter(double value, Temperature from, Temperature to) {
        //1.convert to centigrade
        switch (from) {
            case KELVIN:case SI: value -= 273.15D; break;
            case FAHRENHEIT: value = (value - 32) / 1.8; break;
        }
        //2.convert to target
        switch (to) {
            case KELVIN:case SI: value += 273.15D; break;
            case FAHRENHEIT: value = 32 + value * 1.8; break;
        }

        return value;
    }

    /**
     * Conversion unit of pressure
     * @param value value to convert
     * @param from unit
     * @param to unit
     * @return result
     */
    public static double pressureConverter(double value, Pressure from, Pressure to) {
        return value * from.value / to.value;
    }

    /**
     * Conversion unit of power
     * @param value value to convert
     * @param from unit
     * @param to unit
     * @return result
     */
    public static double powerConverter(double value, Power from, Power to) {
        return value * from.value / to.value;
    }

    /**
     * Conversion unit of energy
     * @param value value to convert
     * @param from unit
     * @param to unit
     * @return result
     */
    public static double energyConverter(double value, Energy from, Energy to) {
        return value * from.value / to.value;
    }

    /**
     * Conversion unit of force
     * @param value value to convert
     * @param from unit
     * @param to unit
     * @return result
     */
    public static double forceConverter(double value, Force from, Force to) {
        return value * from.value / to.value;
    }

    /**
     * Conversion unit of time
     * @param value value to convert
     * @param from unit
     * @param to unit
     * @return result
     */
    public static double timeConverter(double value, Time from, Time to) {
        return value * from.value / to.value;
    }

    /**
     * Conversion unit of speed
     * @param value value to convert
     * @param from unit
     * @param to unit
     * @return result
     */
    public static double speedConverter(double value, Speed from, Speed to) {
        return value * from.value / to.value;
    }

    /**
     * Conversion unit of angle
     * @param value value to convert
     * @param from unit
     * @param to unit
     * @return result
     */
    public static double angleConverter(double value, Angle from, Angle to) {
        return value * from.value / to.value;
    }

    /**
     * Conversion unit of storage
     * @param value value to convert
     * @param from unit
     * @param to unit
     * @return result
     */
    public static double storageConverter(double value, Storage from, Storage to) {
        return value * from.value / to.value;
    }
}
