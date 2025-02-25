package xperamentals.OpenCV.pipelines;

import org.opencv.core.*;
import java.util.ArrayList;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class blueSample extends OpenCvPipeline {

    public Scalar lowerRGBA = new Scalar(0.0, 0.0, 98.0, 0.0);
    public Scalar upperRGBA = new Scalar(82.0, 255.0, 255.0, 255.0);
    private Mat rgbaBinaryMat = new Mat();

    private ArrayList<MatOfPoint> contours = new ArrayList<>();
    private Mat hierarchy = new Mat();

    public Scalar lineColor = new Scalar(0.0, 255.0, 255.0, 0.0);
    public int lineThickness = 1;

    private Mat inputContours = new Mat();

    private MatOfPoint2f approxPolyDp = new MatOfPoint2f();
    private MatOfPoint2f approxPolyDp2f = new MatOfPoint2f();
    private MatOfPoint2f contours2f = new MatOfPoint2f();
    private ArrayList<MatOfPoint> filteredRectangleContours = new ArrayList<>();

    @Override
    public Mat processFrame(Mat input) {
        Core.inRange(input, lowerRGBA, upperRGBA, rgbaBinaryMat);

        contours.clear();
        hierarchy.release();
        Imgproc.findContours(rgbaBinaryMat, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        input.copyTo(inputContours);
        Imgproc.drawContours(inputContours, contours, -1, lineColor, lineThickness);

        filteredRectangleContours.clear();
        for(MatOfPoint contour : contours) {
            contour.convertTo(contours2f, CvType.CV_32FC2);

            Imgproc.approxPolyDP(contours2f, approxPolyDp2f, ((100.0 - 100) / 100.0) * Imgproc.arcLength(contours2f, true), true);
            approxPolyDp2f.convertTo(approxPolyDp, CvType.CV_32S);

            if(approxPolyDp.size().height == 4) {
                filteredRectangleContours.add(contour);
            }

            contours2f.release();
            approxPolyDp.release();
            approxPolyDp2f.release();
        }

        return inputContours;
    }
}
