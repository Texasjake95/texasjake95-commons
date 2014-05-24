package com.texasjake95.commons.vector;

import java.io.Serializable;

import com.texasjake95.commons.helpers.MathHelper;

public class Vector2D implements Serializable {
	
	private static final long serialVersionUID = 6479562263023348410L;
	protected double x;
	protected double y;
	
	public Vector2D()
	{
	}
	
	public Vector2D(double x)
	{
		this.x = x;
	}
	
	public Vector2D(double x, double y)
	{
		this(x);
		this.y = y;
	}
	
	public Vector2D addition(Vector2D vec)
	{
		double VecX = this.x + vec.x;
		double VecY = this.y + vec.y;
		return new Vector2D(VecX, VecY);
	}
	
	public Vector2D addition(Vector2D... vecs)
	{
		Vector2D current = new Vector2D();
		for (Vector2D vec : vecs)
			current = current.addition(vec);
		return current;
	}
	
	public Vector2D subtaction(Vector2D vec)
	{
		double VecX = this.x - vec.x;
		double VecY = this.y - vec.y;
		return new Vector2D(VecX, VecY);
	}
	
	public Vector2D subtaction(Vector2D... vecs)
	{
		Vector2D current = new Vector2D();
		for (Vector2D vec : vecs)
			current = current.subtaction(vec);
		return current;
	}
	
	public double dotProduct(Vector2D vec)
	{
		double VecX = vec.x * this.x;
		double VecY = vec.y * this.y;
		return VecX + VecY;
	}
	
	public double dotProduct(Vector2D... vecs)
	{
		double VecX = this.x;
		double VecY = this.y;
		for (Vector2D vec : vecs)
		{
			VecX *= vec.x;
			VecY *= vec.y;
		}
		return VecX + VecY;
	}
	
	public Vector2D multiply(double scalar)
	{
		return new Vector2D(this.x * scalar, this.y * scalar);
	}
	
	@Override
	public String toString()
	{
		return this.constructString() + " Magnitude: " + this.getMagnitude();
	}
	
	public double getAngleR()
	{
		double angle = (double) Math.atan(this.y / this.x);
		if (y < 0)
		{
			angle += Math.PI;
		}
		return angle;
	}
	
	public double getAngleD()
	{
		double radians = this.getAngleR();
		double degrees = (double) (radians * 180 / Math.PI);
		return degrees;
	}
	
	public double getAngleMadeWithVector(Vector2D vec)
	{
		double vecMag = vec.getMagnitude();
		double dot = this.dotProduct(vec);
		double thisMag = this.getMagnitude();
		double ratio = dot / (thisMag * vecMag);
		if (ratio > 1)
			ratio = 1;
		if (ratio < -1)
			ratio = -1;
		ratio = MathHelper.round(ratio, 4);
		double angle = (double) Math.acos(ratio);
		angle = MathHelper.round(angle, 4);
		return angle;
	}
	
	public double getMagnitude()
	{
		double Xsq = x * x;
		double Ysq = y * y;
		return (double) Math.sqrt(Xsq + Ysq);
	}
	
	protected String constructString()
	{
		return x + ":i" + " " + y + ":j";
	}
}
