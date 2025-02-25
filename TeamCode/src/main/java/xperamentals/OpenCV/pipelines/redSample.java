package xperamentals.OpenCV.pipelines;

import org.opencv.core.*;
import java.util.*;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class redSample extends OpenCvPipeline {

    public Scalar lowerRGBA = new Scalar(126.0, 0.0, 0.0, 0.0);
    public Scalar upperRGBA = new Scalar(255.0, 69.0, 129.0, 255.0);
    private Mat rgbaBinaryMat = new Mat();

    private ArrayList<MatOfPoint> contours = new ArrayList<>();
    private Mat hierarchy = new Mat();

    private MatOfPoint2f contours2f = new MatOfPoint2f();
    private ArrayList<RotatedRect> contoursRotRects = new ArrayList<>();

    public Scalar lineColor = new Scalar(0.0, 255.0, 0.0, 0.0);
    public int lineThickness = 2;

    private Mat inputRotRects = new Mat();

    @Override
    public Mat processFrame(Mat input) {
        Core.inRange(input, lowerRGBA, upperRGBA, rgbaBinaryMat);

        contours.clear();
        hierarchy.release();
        Imgproc.findContours(rgbaBinaryMat, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        contoursRotRects.clear();
        for(MatOfPoint points : contours) {
            contours2f.release();
            points.convertTo(contours2f, CvType.CV_32F);

            contoursRotRects.add(Imgproc.minAreaRect(contours2f));
        }

        input.copyTo(inputRotRects);
        for(RotatedRect rect : contoursRotRects) {
            if(rect != null) {
                Point[] rectPoints = new Point[4];
                rect.points(rectPoints);
                MatOfPoint matOfPoint = new MatOfPoint(rectPoints);

                Imgproc.polylines(inputRotRects, Collections.singletonList(matOfPoint), true, lineColor, lineThickness);
            }
        }

        return inputRotRects;
    }
}
