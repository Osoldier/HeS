package me.it.hes.space.planets;

import me.it.lib.graphics.*;
import me.it.lib.math.*;

public class PlanetShader extends Shader{

	private final static String VERT_FILE = "src/me/it/hes/space/solarsystems/ss.vert";
	private final static String FRAG_FILE = "src/me/it/hes/space/solarsystems/ss.frag";
	
	private int mlMatLoc, vwMatLoc, prMatLoc;
	private ModelMatrix mlMat;
	private ViewMatrix vwMat;
	private ProjectionMatrix prMat;
	
	public PlanetShader() {
		super(VERT_FILE, FRAG_FILE);
	}
	
	public void loadOnceUniforms() {
		this.setUniform(vwMatLoc, vwMat);
		this.setUniform(prMatLoc, prMat);
	}
	
	@Override
	public void loadUniforms() {
		this.setUniform(mlMatLoc, mlMat);
	}

	public ModelMatrix getMlMat() {
		return mlMat;
	}

	public void setMlMat(ModelMatrix mlMat) {
		this.mlMat = mlMat;
	}

	public ViewMatrix getVwMat() {
		return vwMat;
	}

	public void setVwMat(ViewMatrix vwMat) {
		this.vwMat = vwMat;
	}

	public ProjectionMatrix getPrMat() {
		return prMat;
	}

	public void setPrMat(ProjectionMatrix prMat) {
		this.prMat = prMat;
	}

}
