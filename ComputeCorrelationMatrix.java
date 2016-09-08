import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.regression.SimpleRegression;


public class ComputeCorrelationMatrix {
	public RealMatrix computeCorrelationMatrix(double[][] data) {
		return computeCorrelationMatrix(new BlockRealMatrix(data));
	}

	public RealMatrix computeCorrelationMatrix(RealMatrix matrix) {
		checkSufficientData(matrix);
		int nVars = matrix.getColumnDimension();
		RealMatrix outMatrix = new BlockRealMatrix(nVars, nVars);
		for (int i = 0; i < nVars; i++) {
			for (int j = 0; j < i; j++) {
				double corr = correlation(matrix.getColumn(i),
						matrix.getColumn(j));
				outMatrix.setEntry(i, j, corr);
				outMatrix.setEntry(j, i, corr);
			}
			outMatrix.setEntry(i, i, 1.0D);
		}
		return outMatrix;
	}

	public double correlation(double[] xArray, double[] yArray) {
		SimpleRegression regression = new SimpleRegression();
		if (xArray.length != yArray.length) {
			throw new DimensionMismatchException(xArray.length, yArray.length);
		}
		if (xArray.length < 2) {
			throw new MathIllegalArgumentException(
					LocalizedFormats.INSUFFICIENT_DIMENSION,
					new Object[] { Integer.valueOf(xArray.length),
							Integer.valueOf(2) });
		}
		for (int i = 0; i < xArray.length; i++) {
			regression.addData(xArray[i], yArray[i]);
		}
		return regression.getR();
	}
	private void checkSufficientData(RealMatrix matrix) {
		int nRows = matrix.getRowDimension();
		int nCols = matrix.getColumnDimension();
		if ((nRows < 2) || (nCols < 2)) {
			throw new MathIllegalArgumentException(
					LocalizedFormats.INSUFFICIENT_ROWS_AND_COLUMNS,
					new Object[] { Integer.valueOf(nRows),
							Integer.valueOf(nCols) });
		}
	}
}
