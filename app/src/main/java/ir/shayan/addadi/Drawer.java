package ir.shayan.addadi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import kotlin.Pair;


public class Drawer extends View {
    private List<Pair<Float, Float>> points = new ArrayList<>();

    Paint paint;
    private List<Pair<Double, Double>> tmpPoints;

    public Drawer(Context context) {
        super(context);
        setup();
    }

    private void setup() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.d("Drawer", "" + tmpPoints.size());

                double xMin = tmpPoints.get(0).getFirst();
                double xMax = tmpPoints.get(tmpPoints.size() - 1).getFirst();

                Log.d("drawer", "" + (xMax - xMin));

                double yMin = getYMin(tmpPoints);
                double yMax = getYMax(tmpPoints);

                Log.d("Drawer", "" + (yMax - yMin));

                points.clear();

                for (Pair<Double, Double> pair : tmpPoints) {
                    points.add(calculateNewPosition(pair, xMax - xMin, yMax - yMin, xMin, yMin));
                }
            }
        });
    }

    public Drawer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public Drawer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }

    public Drawer(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setup();
    }

    public void setDrawPoints(List<Pair<Double, Double>> points) {
        this.tmpPoints = points;

        invalidate();
    }

    private Pair<Float, Float> calculateNewPosition(Pair<Double, Double> pair, double xLen,
                                                    double yLen, double xMin, double yMin) {
        Log.d("drawer", "x : " + pair.getFirst() + " y: " + pair.getSecond());
        float newX = (float) (((pair.getFirst() / xLen) * getWidth() - (xMin / xLen) * getWidth()));
        float newY = (float) (((pair.getSecond() / yLen) * getHeight() - (yMin / yLen) * getHeight()));
        newY = (-newY + getHeight());
        Log.d("Drawer", "x : " + newX + " y: " + newY);
        return new Pair<>(newX, newY);
    }

    private double getYMax(List<Pair<Double, Double>> points) {
        double max = Double.MIN_VALUE;
        for (Pair<Double, Double> pair : points) {
            if (pair.getSecond() > max) max = pair.getSecond();
        }
        return max;
    }

    private double getYMin(List<Pair<Double, Double>> points) {
        double min = Double.MAX_VALUE;
        for (Pair<Double, Double> pair : points) {
            if (pair.getSecond() < min) {
                min = pair.getSecond();
            }
        }
        return min;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < points.size() - 1; i++) {
            canvas.drawLine(
                    points.get(i).getFirst(),
                    points.get(i).getSecond(),
                    points.get(i + 1).getFirst(),
                    points.get(i + 1).getSecond(),
                    paint);
        }
    }
}
