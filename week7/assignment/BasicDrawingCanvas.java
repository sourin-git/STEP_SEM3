public class BasicDrawingCanvas {
    static abstract class Shape {
        private static int shapeCount;
        private final String shapeId;

        Shape() {
            shapeCount++;
            shapeId = String.format("SHP-%04d", 1000 + shapeCount);
        }

        abstract double calculateArea();

        abstract void scaleDimensions(double xFactor, double yFactor);

        void scale(double factor) {
            scale(factor, factor);
        }

        void scale(double xFactor, double yFactor) {
            scaleDimensions(xFactor, yFactor);
        }

        String getShapeId() {
            return shapeId;
        }
    }

    static class CircleShape extends Shape {
        private double radiusX;
        private double radiusY;

        CircleShape(double radius) {
            if (radius <= 0) {
                throw new IllegalArgumentException("Radius must be positive");
            }
            radiusX = radius;
            radiusY = radius;
        }

        @Override
        double calculateArea() {
            return Math.PI * radiusX * radiusY;
        }

        @Override
        void scaleDimensions(double xFactor, double yFactor) {
            radiusX *= xFactor;
            radiusY *= yFactor;
        }
    }

    static class SquareShape extends Shape {
        private double width;
        private double height;

        SquareShape(double side) {
            if (side <= 0) {
                throw new IllegalArgumentException("Side must be positive");
            }
            width = side;
            height = side;
        }

        @Override
        double calculateArea() {
            return width * height;
        }

        @Override
        void scaleDimensions(double xFactor, double yFactor) {
            width *= xFactor;
            height *= yFactor;
        }
    }

    static void printArea(Shape shape) {
        System.out.println(shape.calculateArea());
    }

    public static void main(String[] args) {
        CircleShape circle = new CircleShape(5.0);
        SquareShape square = new SquareShape(4.0);
        System.out.printf("Circle area: %.2f%n", circle.calculateArea());
        System.out.println("Square area: " + square.calculateArea());
        square.scale(2.0);
        System.out.println("Scaled square area: " + square.calculateArea());
        printArea(circle);
        System.out.println(circle.getShapeId());
    }
}