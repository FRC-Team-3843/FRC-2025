package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants;
import frc.robot.subsystems.ClawArm;
import frc.robot.subsystems.ClawElevator;
import frc.robot.subsystems.Lifter;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.awt.geom.PathIterator;

public class MotionManager extends SequentialCommandGroup {
    
    ClawArm clawArm;
    ClawElevator clawElevator;
    Lifter lifter;

    double clawArmCurrentPosition;
    double clawElevatorCurrentPosition;
    double liftCurrentPosition;

    // Danger Zones X = Lift, Y = Arm
    Point2D.Double[] elevator0NoAlgaeDanger = {
        new Point2D.Double(0, 0.5),
        new Point2D.Double(40, 8),
        new Point2D.Double(118, 8),
        new Point2D.Double(118, 15),
        new Point2D.Double(98, 15),
        new Point2D.Double(98, 29),
        new Point2D.Double(56, 29),
        new Point2D.Double(0, 21)
    };
    
    // Danger Zones X = Lift, Y = Arm
    // Lower part of polygon needs to be defined
    Point2D.Double[] elevator20NoAlgaeDanger = {
        new Point2D.Double(0, 13),
        new Point2D.Double(17.5, 16),
        new Point2D.Double(35, 19),
        new Point2D.Double(80, 19),
        new Point2D.Double(80, 0),
        new Point2D.Double(0, 0),
    };

    //TODO: Define the danger zones for two levels with algae
    

    Path2D.Double elevator0NoAlgaeDangerPolygon = createPolygon(elevator0NoAlgaeDanger);
    Path2D.Double elevator20NoAlgaeDangerPolygon = createPolygon(elevator20NoAlgaeDanger);

    MotionManager(ClawArm m_clawArm, Double clawArmSetPosition, ClawElevator m_clawElevator, Lifter m_lifter){
        this(m_clawArm, clawArmSetPosition , m_clawElevator, m_clawElevator.getPosition(), m_lifter, m_lifter.getPosition());
    }

    MotionManager(ClawArm m_clawArm, ClawElevator m_clawElevator, Double clawElevatorSetPosition, Lifter m_lifter){
        this(m_clawArm, m_clawArm.getPosition() , m_clawElevator, clawElevatorSetPosition, m_lifter, m_lifter.getPosition());
    }

    MotionManager(ClawArm m_clawArm, ClawElevator m_clawElevator, Lifter m_lifter, Double liftSetPosition){
        this(m_clawArm, m_clawArm.getPosition() , m_clawElevator, m_clawElevator.getPosition(), m_lifter, liftSetPosition);
    }
    
    MotionManager(ClawArm m_clawArm, ClawElevator m_clawElevator, Double clawElevatorSetPosition, Lifter m_lifter, Double liftSetPosition){
        this(m_clawArm, m_clawArm.getPosition() , m_clawElevator, clawElevatorSetPosition, m_lifter, liftSetPosition);
    }
    
    MotionManager(ClawArm m_clawArm, Double clawArmSetPosition, ClawElevator m_clawElevator, Lifter m_lifter, Double liftSetPosition){
        this(m_clawArm, clawArmSetPosition, m_clawElevator, m_clawElevator.getPosition(), m_lifter, liftSetPosition);
    }
    
    MotionManager(ClawArm m_clawArm, Double clawArmSetPosition, ClawElevator m_clawElevator, Double clawElevatorSetPosition, Lifter m_lifter){
        this(m_clawArm, clawArmSetPosition, m_clawElevator, clawElevatorSetPosition, m_lifter, m_lifter.getPosition());
    }

    MotionManager(ClawArm m_clawArm, Double clawArmSetPosition, ClawElevator m_clawElevator, Double clawElevatorSetPosition, Lifter m_lifter, Double liftSetPosition) {
        addRequirements(m_clawArm);
        addRequirements(m_clawElevator);
        addRequirements(m_lifter);

        Double clawArmCurrentPosition = m_clawArm.getPosition();
        Double clawElevatorCurrentPosition = m_clawElevator.getPosition();
        Double liftCurrentPosition = m_lifter.getPosition();

        System.out.println("Starting Motion Manager");
        
        /*
        if(clawArmSetPosition == null || clawElevatorSetPosition == null || liftSetPosition == null) {
            System.err.println("Error: Set positions cannot be null.");
            return;
        }
        else if(((clawElevatorCurrentPosition < 18 || clawElevatorSetPosition < 18) && isPointInArea(new Point2D.Double(liftSetPosition, clawArmSetPosition), elevator0NoAlgaeDangerPolygon)) || ((clawElevatorCurrentPosition >= 18 || clawElevatorSetPosition >= 18) &&(isPointInArea(new Point2D.Double(liftSetPosition, clawArmSetPosition), elevator20NoAlgaeDangerPolygon)))) {
            System.err.println("Error: The set position is in a danger zone.");
            return;
        }
        else if(clawArmCurrentPosition == clawArmSetPosition && clawElevatorCurrentPosition == clawElevatorSetPosition && liftCurrentPosition == liftSetPosition) {
            System.err.println("Error: Already at set position.");
            return;
        }
        else if((clawElevatorCurrentPosition < 18 && clawElevatorSetPosition > 18) || (clawElevatorCurrentPosition > 18 && clawElevatorSetPosition < 18)){
            System.out.println("Starting Elevator Transfer Motion");
            System.out.println(clawElevatorCurrentPosition);
            System.out.println(clawElevatorSetPosition);
            if(m_lifter.isClear()) {
                addCommands(
                    Commands.runOnce(() -> m_clawElevator.setPos(clawElevatorSetPosition)),
                    new WaitUntilCommand(() -> m_clawElevator.isAtPosition(clawElevatorSetPosition)),
                    new MotionManager(m_clawArm, clawArmSetPosition, m_clawElevator, clawElevatorSetPosition, m_lifter, liftSetPosition)
                );
            }
            else if(!m_lifter.isClear()){
                addCommands(
                    new LifterClearanceCommand(m_clawArm, m_clawElevator, m_lifter),
                    new WaitUntilCommand(() -> m_lifter.isClear()),
                    new MotionManager(m_clawArm, clawArmSetPosition, m_clawElevator, clawElevatorSetPosition, m_lifter, liftSetPosition)
                );
            }
        }
        else if(Math.abs(clawElevatorCurrentPosition - clawElevatorSetPosition) > 2){
            System.out.println("Starting Elevator Motion");
            addCommands(
                Commands.runOnce(() -> m_clawElevator.setPos(clawElevatorSetPosition)),
                new WaitUntilCommand(() -> m_clawElevator.isAtPosition(clawElevatorSetPosition)),
                new MotionManager(m_clawArm, clawArmSetPosition, m_clawElevator, clawElevatorSetPosition, m_lifter, liftSetPosition)
            );
        }            
        else if((Math.abs(clawArmCurrentPosition - clawArmSetPosition) > 2 && Math.abs(liftCurrentPosition - liftSetPosition) > 2) && checkFullMotion(clawArmSetPosition, liftSetPosition, clawArmCurrentPosition, liftCurrentPosition, clawElevatorCurrentPosition)){
            System.out.println("Making double move");
            addCommands(
                Commands.runOnce(() -> m_lifter.setPos(liftSetPosition)),
                Commands.runOnce(() -> m_clawArm.setPos(clawArmSetPosition)),
                Commands.runOnce(() -> m_clawElevator.setPos(clawElevatorSetPosition))
            );
        }  
        else if((Math.abs(liftCurrentPosition - liftSetPosition) > 2) && checkLiftMotion(clawArmSetPosition, liftSetPosition, clawArmCurrentPosition, liftCurrentPosition, clawElevatorCurrentPosition)) {
            System.out.println("Moving Lift0");
            if(checkArmMotion(clawArmSetPosition, liftSetPosition, clawArmCurrentPosition, liftSetPosition, clawElevatorCurrentPosition)){
                System.out.println("Moving Lift1");
                addCommands(
                    Commands.runOnce(() -> m_lifter.setPos(liftSetPosition)),
                    new WaitUntilCommand(() -> m_lifter.isAtPosition(liftSetPosition)),
                    Commands.runOnce(() -> m_clawArm.setPos(clawArmSetPosition))
                );
            }
            else{
                System.out.println("Moving Lift2");
                addCommands(
                    new LifterClearanceCommand(m_clawArm, m_clawElevator, m_lifter),
                    new WaitUntilCommand(() -> m_lifter.isClear()),
                    Commands.runOnce(() -> m_clawArm.setPos(clawArmSetPosition)),
                    new WaitUntilCommand(() -> m_clawArm.isAtPosition(clawArmSetPosition)),
                    new MotionManager(m_clawArm, clawArmSetPosition, m_clawElevator, clawElevatorSetPosition, m_lifter, liftSetPosition)
                );
            }
        }
        else if((Math.abs(clawArmCurrentPosition - clawArmSetPosition) > 2) && checkArmMotion(clawArmSetPosition, liftSetPosition, clawArmCurrentPosition, liftCurrentPosition, clawElevatorCurrentPosition)) {
            System.out.println("Moving Arm");
            if(checkLiftMotion(clawArmSetPosition, liftSetPosition, clawArmSetPosition, liftCurrentPosition, clawElevatorCurrentPosition)){
                addCommands(
                    Commands.runOnce(() -> m_clawArm.setPos(clawArmSetPosition)),
                    new WaitUntilCommand(() -> m_clawArm.isAtPosition(clawArmSetPosition)),
                    Commands.runOnce(() -> m_lifter.setPos(liftSetPosition))
                );
            }
            else{
                addCommands(
                    new ArmClearanceCommand(m_clawArm, m_clawElevator, m_lifter),
                    new WaitUntilCommand(() -> m_clawArm.isClear()),
                    Commands.runOnce(() -> m_lifter.setPos(liftSetPosition)),
                    new WaitUntilCommand(() -> m_lifter.isAtPosition(liftSetPosition)),
                    new MotionManager(m_clawArm, clawArmSetPosition, m_clawElevator, clawElevatorSetPosition, m_lifter, liftSetPosition)
                );
            }
        }
        else {
            System.err.println("Error: Can't move to set position. ");
            return;
        }

        */
    }

    public boolean checkFullMotion(double m_clawArmSetPosition, double m_liftSetPosition, double m_clawArmCurrentPosition, double m_liftCurrentPosition, double m_clawElevatorCurrentPosition) {

        Path2D.Double motionPolygon = createPolygon(createQuadrilateral(m_liftCurrentPosition, m_clawArmCurrentPosition, m_liftSetPosition, m_clawArmSetPosition));

        if((m_clawElevatorCurrentPosition < 18) && polygonsIntersect(motionPolygon, elevator0NoAlgaeDangerPolygon))
                return false; 
        else if((m_clawElevatorCurrentPosition >= 18) && polygonsIntersect(motionPolygon, elevator20NoAlgaeDangerPolygon))
                return false;
        return true;

    }

    public boolean checkLiftMotion(double m_clawArmSetPosition, double m_liftSetPosition, double m_clawArmCurrentPosition, double m_liftCurrentPosition, double m_clawElevatorCurrentPosition) {

        Line2D.Double motionLine = createLine(m_liftCurrentPosition, m_clawArmCurrentPosition, m_liftSetPosition, m_clawArmCurrentPosition);

        if((m_clawElevatorCurrentPosition < 20) && (lineIntersectsPolygon(motionLine, elevator0NoAlgaeDangerPolygon))) 
                return false;
        else if((m_clawElevatorCurrentPosition >= 20) && (lineIntersectsPolygon(motionLine, elevator20NoAlgaeDangerPolygon)))
                return false;
        return true;      

    }

    public boolean checkArmMotion(double m_clawArmSetPosition, double m_liftSetPosition, double m_clawArmCurrentPosition, double m_liftCurrentPosition, double m_clawElevatorCurrentPosition) {

        Line2D.Double motionLine = createLine(m_liftCurrentPosition, m_clawArmCurrentPosition, m_liftCurrentPosition, m_clawArmSetPosition);
        
        if((m_clawElevatorCurrentPosition < 20) && (lineIntersectsPolygon(motionLine, elevator0NoAlgaeDangerPolygon))) 
                return false;
        else if((m_clawElevatorCurrentPosition >= 20) && (lineIntersectsPolygon(motionLine, elevator20NoAlgaeDangerPolygon)))
                return false;
        return true;     
    }



    public static Point2D.Double[] createQuadrilateral(double x1, double y1, double x2, double y2) {
        // Check for invalid input: if the points are the same
         if (x1 == x2 && y1 == y2) {
             System.err.println("Error: The two corners cannot be the same point.");
             return null;
         }
         double minX = Math.min(x1, x2);
         double maxX = Math.max(x1, x2);
         double minY = Math.min(y1, y2);
         double maxY = Math.max(y1, y2);
 
         Point2D.Double bottomLeft  = new Point2D.Double(minX, minY);
         Point2D.Double bottomRight = new Point2D.Double(maxX, minY);
         Point2D.Double topRight    = new Point2D.Double(maxX, maxY);
         Point2D.Double topLeft     = new Point2D.Double(minX, maxY);
 
         return new Point2D.Double[]{bottomLeft, bottomRight, topRight, topLeft};
     }

    
    /**
     * Creates a Path2D.Double (polygon) from an array of Point2D.Double objects.
     *
     * @param polygonPoints An array of Point2D.Double objects representing the vertices of the polygon.
     * @return A Path2D.Double object representing the polygon. Returns null if the input is invalid.
     */
    public static Path2D.Double createPolygon(Point2D.Double[] points) {
        if (points == null || points.length < 3) {
            System.err.println("Error: Polygon must have at least 3 points.");
            return null; // Or throw an IllegalArgumentException
        }

        Path2D.Double polygon = new Path2D.Double();
        polygon.moveTo(points[0].getX(), points[0].getY()); // Start the path

        for (int i = 1; i < points.length; i++) {
            polygon.lineTo(points[i].getX(), points[i].getY()); // Add lines to each point
        }

        polygon.closePath(); // Close the polygon (connect last point to first)
        return polygon;
    }


    /**
     * Creates a Line2D.Double object from four doubles (x1, y1, x2, y2).
     *
     * @param x1 The x-coordinate of the starting point of the line.
     * @param y1 The y-coordinate of the starting point of the line.
     * @param x2 The x-coordinate of the ending point of the line.
     * @param y2 The y-coordinate of the ending point of the line.
     * @return A Line2D.Double object representing the line.
     */
    public static Line2D.Double createLine(double x1, double y1, double x2, double y2) {
        return new Line2D.Double(x1, y1, x2, y2);
    }


    /**
     * Checks if two polygons intersect (including touching).
     *
     * @param polygon1Points An array of Point2D.Double objects representing the vertices of the first polygon.
     * @param polygon2Points An array of Point2D.Double objects representing the vertices of the second polygon.
     * @return True if the polygons intersect or touch, false otherwise.
     */

    public static boolean polygonsIntersect(Path2D.Double polygon1, Path2D.Double polygon2) {
        if (polygon1 == null || polygon2 == null) {
            return false; // Handle null polygons
        }
        Area area1 = new Area(polygon1);
        Area area2 = new Area(polygon2);

        area1.intersect(area2); // Perform the intersection

        return !area1.isEmpty(); // If the intersection is not empty, they intersect
    }


    /**
     * Checks if a line intersects a polygon (including touching).
     *
     * @param line The Line2D.Double object representing the line segment.
     * @param polygon The Path2D.Double object representing the polygon.
     * @return True if the line intersects or touches the polygon, false otherwise.
     */
    public static boolean lineIntersectsPolygon(Line2D.Double line, Path2D.Double polygon) {
        if (line == null || polygon == null) {
           return false; // Handle null line or polygon
       }

       Point2D.Double lineStart = new Point2D.Double(line.getX1(), line.getY1());
       Point2D.Double lineEnd = new Point2D.Double(line.getX2(), line.getY2());

       // Check if either endpoint is inside the polygon
       if (polygon.contains(lineStart) || polygon.contains(lineEnd)) {
           return true;
       }

       // Check if the line intersects any of the polygon's edges
       Point2D.Double[] points = getPolygonPoints(polygon);  //Extract vertices

       for (int i = 0; i < points.length; i++) {
           int j = (i + 1) % points.length;  // Next point (wraps around to the start)
           Line2D.Double edge = new Line2D.Double(points[i], points[j]);
           if (line.intersectsLine(edge)) {
               return true; // Line intersects an edge
           }
       }

       return false; // No intersection found
   }

    /**
     * Helper function to extract the vertices from a Path2D.Double polygon.
     *
     * @param polygon The Path2D.Double object representing the polygon.
     * @return An array of Point2D.Double objects representing the vertices of the polygon.
     */
    private static Point2D.Double[] getPolygonPoints(Path2D.Double polygon) {
        if (polygon == null) {
            return new Point2D.Double[0];
        }

        List<Point2D.Double> points = new ArrayList<>(); 

        double[] coords = new double[6];
        PathIterator pathIterator = polygon.getPathIterator(null);

        while (!pathIterator.isDone()) {
            int segType = pathIterator.currentSegment(coords);
            switch (segType) {
                case java.awt.geom.PathIterator.SEG_MOVETO:
                    points.add(new Point2D.Double(coords[0], coords[1]));
                    break;
                case java.awt.geom.PathIterator.SEG_LINETO:
                    points.add(new Point2D.Double(coords[0], coords[1]));
                    break;
                case java.awt.geom.PathIterator.SEG_CLOSE:
                    // Close the polygon - No point added as this just indicates end
                    break;
            }
            pathIterator.next();
        }
        return points.toArray(new Point2D.Double[0]);
    }

    public static boolean isPointInArea(Point2D.Double point, Path2D.Double polygon) {
        if (point == null || polygon == null) {
          return false; // Handle null input
      }

    return polygon.contains(point);
    }

}


