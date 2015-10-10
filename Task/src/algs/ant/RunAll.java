package algs.ant;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.resources.FileResource;
import org.apache.tools.ant.types.Path;

import java.util.ArrayList;
import java.util.Date;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.util.Iterator;
import java.lang.reflect.Method;

/**
 * Execute all main classes within Performance directory
 */
public class RunAll extends Task {

    private ArrayList<FileSet> filesets = new ArrayList<FileSet>();
    private ArrayList<Path> paths = new ArrayList<Path>();

    // output directory
    private File outputDir = null;

    // output files appear in this directory
    public void setOutput(String s) {
	outputDir = new File (s);
    }

    // The method executing the task
    public void execute() throws BuildException {

	if (outputDir == null) {
	    throw new BuildException ("no output directory declared!");
	}

	if (!outputDir.exists()) {
	    throw new BuildException ("Output directory doesn't exist:" + outputDir);
	}

	String javaHome = System.getProperty("java.home");
	String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
        String classpath = System.getProperty("java.class.path");

	// add on our own paths
	String allPaths = "";
	for (Path p : paths) {
	    classpath  += ":" + p.toString();
	}

	for (FileSet fs : filesets) {

	    for (Iterator it = fs.iterator(); it.hasNext(); ) {
		FileResource res = (FileResource) it.next();
		
		// convert name into CLASS (trim .java and replace slashes)
		String name = res.getName();
		name = name.substring (0, name.length()-5);
		String className = name.replace('/', '.');
	    
		try {

		    ProcessBuilder builder = new ProcessBuilder(javaBin, "-cp", classpath, className);
		    Process process = builder.start();

		    // process output
		    InputStream is = process.getInputStream();
		    InputStreamReader isr = new InputStreamReader(is);
		    BufferedReader br = new BufferedReader (isr);
		    
		    File outFile = new File (outputDir, className + ".output");
		    PrintStream ps = new PrintStream (outFile);

		    // generic start/stop info for each output file.
		    Date d = new Date();
		    ps.println ("Start:" + d.getTime() + " [" + d + "]");

		    String s;
		    int nb = 0;
		    while ((s = br.readLine()) != null) {
			nb += s.length();
			ps.println(s);
		    }

		    // generic end
		    d = new Date();
		    ps.println ("End:" + d.getTime() + " [" + d + "]");

		    ps.close();

		    int ev = process.exitValue();
		    System.out.println (className + " [" + nb + " bytes output, exit code:" + ev + "]");
		    
		} catch (Exception e) {
		    throw new BuildException ("unable to process class " + className + ": " + e.getMessage());
		}
	    }
	}
    }

    // Add the fileset
    public void addFileset(FileSet fs) {
	filesets.add(fs);
    }

    // Add the paths
    public void addClasspath(Path p) {
	paths.add(p);
    }


}
