### Workshop
# Met Maven kun je Alles Bouwen
[Utrecht Java Meetup.com](https://www.meetup.com/meetup-group-pUGgAXiQ) - 7 feb 2017

![ordinalogo](img/ordina.png)
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
![jtechlogo](img/jtech.jpg)

(c) 2018, Bert Koorengevel


## Introduction

In this workshop you will transform a standard Maven Java project producing a .jar file 
into one that produces a .zip file with your own content in seven easy steps.
Meanwhile you'll learn to work with Maven properties, Maven plugins, and the Maven build lifecycle.

But first you need to do some preparations:
* There is a GitHub repository to clone, 
* You need to open some file browser windows to view some folders,
* And the project needs to be built for the first time.


## Step 0. Getting Ready - Clone the GitHub repository
We have put a very small Java example project for you on GitHub:<br/>
https://github.com/Ordina-JTech/MetMavenKunJeAllesBouwen

* Use the <span style="color:white; background-color:green">&nbsp;Clone or download&nbsp;</span> 
  feature of GitHub to make a **clone** at any location you like on a local disk, 
  or paste the following command in your <small>(Windows)</small> `cmd.exe` 
  or <small>(Mac)</small> `terminal.app` window:

````cmd
git clone https://github.com/Ordina-JTech/MetMavenKunJeAllesBouwen.git
````


## Step 0. Getting Ready - Locating the pom.xml
* View the cloned git repository with Windows Explorer.<br/>
  Ofcourse Mac users should use the Finder.app

You should see a `src` folder, but not yet the `target` folder. 
Also there should be the `pom.xml` which defines this as the root of a Maven project,
and a `README.md` file which contains the GitHub project documentation page
plus the `LICENSE.md` text.

Further on in this workshop we'll refer to this as the ** workspace** folder.<br/>
You'll need this file browser later again, so please leave it open.


## Step 0. Getting Ready - Editing the pom.xml
* Open the `pom.xml` file with a good text editor such as 
  Notepad++, UltraEdit, SublimeText, VS Code, Atom, TextMate.

The first few lines should look like this:

````xml
<?xml version="1.0" encoding="UTF-8"?>
<project>
    <!-- This is the Maven Model version which MUST be 4.0.0 !!! -->
    <modelVersion>4.0.0</modelVersion>
 
    <!-- Identification of the Artifact -->
    <groupId>nl.ordina.jtech</groupId>
    <artifactId>MavenDemo</artifactId>
    <version>0.${revision}</version>
    <packaging>jar</packaging>
````

On line 7 you see the `groupId` is defined as `nl.ordina.jtech`.
You may change that to your own name, so this will become your personal project.

<small>NB: It is adviced to keep the `nl.` prefix in your own groupId.</small>


## Step 0. Getting Ready - Locate your Maven Repository
<small>If you know the whereabouts of your local Maven repository: good!
Otherwise there is a failsafe method to figure it out, and that is to let Maven determine it for you.
The `effective-settings` goal of the `maven-help-plugin` lists out all the currently effective settings in xml format.
Since that may be a lot of output, we'll pipe it through `grep` or `findstr` to show only the line we want to see.</small>

* Windows users: run `cmd.exe` and execute this command:
````ini
mvn help:effective-settings | findstr 'local'
````
* Mac users: open a `terminal.app` session and execute this command:
````ini
mvn help:effective-settings | grep 'local'
````

<small>Also leave this window open, you'll need it.<br/><br/>
You might want to increase the width of the window so you can easily see the quite long output lines
without them getting wrapped onto the next line.
Windows users: right-click on its title bar and select **Properties** (Eigenschappen). 
Use the tab **layout** to adjust the screen buffer size.
Also enable the **smart paste** feature.</small>


## Step 0. Getting Ready - View your Maven Repository
Now you know the exact location of your local Maven repository 
(by default it's the `.m2/repository` folder within your own home folder)
open **another** Windows Explorer or Finder window, 
and navigate to its folder location.
You'll see there is quite a bit of content, unless you have just installed Maven.
All the plugins and jars used and produced by your Maven projects are stored here.

* Confirm that the path to your own `groupid` does not exist yet.


## Step 0. Getting Ready - First Maven build
Since Maven is a command line utility, you need the command prompt or terminal session used previously.
Use the `cd` command to navigate to the root folder of our project (where our pom.xml is located)
and execute:
````
mvn clean install
````

This should report at the end:
````ini
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
````
* Have a look at the **workspace** folder. Maven should have created a `target` folder. 
  Browse around in there and see for yourself what is put there.
* Also have a look at the **local Maven repository**.
  Now there should be a folder containing your own `groupId`.


## Introducing Properties
<small>Have a look at this section of our `pom.xml` file:</small>
````xml
    <artifactId>MavenDemo</artifactId>
    <version>0.${revision}</version>
    <packaging>jar</packaging>
    
    <properties>
        <revision>0</revision>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
````
<small>The `version` of our artifact is set to "**zero dot** `${revision}`"
and further on in the `<properties>` section of the pom the property `revision` is set to an actual value.
It should be no surprise that Maven evaluates the version to the value "**zero dot zero**".<br/><br/>
Properties are especially useful when certain string values are needed at several places in the same pom.xml.
So when the value needs to be changed, you change it at one place only 
and the actual value is applied at all its occurrences.
If you look for other occurrences of `revision` (or better: let your editor do that for you)
you'll find it in a piece of comment we'll activate later on.
</small>
* You may set the revision to 1, so the next build will be version 0.1


## Partially executing the 'default' Maven build lifecycle
* In the command line window, execute this command:
````
mvn clean
````
* Check the **workspace** folder: the `target` folder should have vanished.
* Now execute this:
````
mvn process-resources
````
* Look at the **workspace** folder again.
  There should be a `target` folder with a `classes` subfolder.

* Now execute:
````
mvn compile
````
And have a look at what's in the `target/classes` folder again.


## Execute the full 'default' Maven build lifecycle
<small>https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html#Lifecycle_Reference</small>
lists all the goals that make up the default build lifecycle.
When you executed `mvn process-resources`,
it went through the five other goals first: validate, initialize, generate-sources, process-sources and generate-resources.
For all of those goals there was nothing to do.

* Now let Maven execute the **full** default lifecylce.

What goal did you chose?

Would it matter whether you had picked `install` or `deploy`? <br/>
Can you explain why, or why not?

And what if you had picked `verify`?


OK, enough preparations...

# Step 1


## Step 1: Introducing the maven-antrun-plugin
Please take a look at the pom.xml of our project.

Just under `<build> <plugins>`, at lines 20-42 we have prepared the declaration of the `maven-antrun-plugin`
to save you a lot of typing, with some of its essential parts still commented out.
We're going to use this plugin to perform some small elementary tasks for us.

<small>ANT (**A**nother **N**eat **T**ool) can be seen as the predecessor of Maven:<br/>
it's a Java-targeted platform-independant build tool from the Apache Foundation.<br/>
With ANT you would just chain individual tasks, while Maven already has a structured lifecycle.
<br/><br/>
Have a look here what tasks ANT can perform:<br/>
https://ant.apache.org/manual/tasksoverview.html
<br/><br/>
It's mainly the *file* tasks we're interested in for this project.
</small>


## Step 1: Activating the maven-antrun-plugin
The maven-antrun-plugin did not do anything yet, because it's execution phase (line 26) is still empty.
You can see some prepared `echo` commands. 
It would be nice if these can be executed at the start of the build.

* Lookup in the 
  [Maven Lifecycle Reference](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html#Lifecycle_Reference)
  which is the very first phase of the *default* build lifecycle.

We're going to extend this (yet empty) phase with some self-chosen behaviour.

* Fill in on line 26 the very first phase you found.
* Remove the xml comment tags `<!--` and `-->` on lines 32 & 37,
  but leave the `<echo>` lines in place.


## Step 1: Reviewing the maven-antrun-plugin configuration
So now the `maven-antrun-plugin` has a **Execution** set for the phase **Validate**.
The **Configuration** has a **Target** with four `echo` commands.

The `echo` command will display some text on the console or in the log file.
That can be some literal text, or property content denoted as `${}`. 

* Execute:

````
mvn validate 
````


## Step 1: Output of the maven-antrun-plugin
You should see something like this:

````ini
[INFO] Scanning for projects...
[INFO] ------------------------------------------------------------------------
[INFO] Building MavenDemo 0.0
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-antrun-plugin:1.6:run (default) @ MavenDemo ---
[INFO] Executing tasks

ant-step01:
     [echo] project.basedir =               ~/ordina-jtech/MetMavenKunJeAllesBouwen
     [echo] project.build.sourceDirectory = ~/ordina-jtech/MetMavenKunJeAllesBouwen/src/main/java
     [echo] project.build.directory =       ~/ordina-jtech/MetMavenKunJeAllesBouwen/target
     [echo] project.build.outputDirectory = ~/ordina-jtech/MetMavenKunJeAllesBouwen/target/classes
[INFO] Executed tasks
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 0.994 s
[INFO] Finished at: 2018-02-04T19:06:59+01:00
[INFO] Final Memory: 9M/245M
[INFO] ------------------------------------------------------------------------
````
<small>I always forget which is the `/target` or the `/target/classes` &nbsp; folder, so it's good to have a reference.</small>


## Step 1: Output of the maven-antrun-plugin
The four printed properties are predefined by Maven.
There's a short list of this type of Maven properties online here in the POM Reference guide: 
https://maven.apache.org/pom.html#Build_Element

These properties are derived from the *effective* POM 
which Maven builds up in memory during execution of the project.
Especially with folder names, it pays off to refer to the standard Maven properties
and not create a property holding the target folder name yourself.

Via properties you can refer to some of the xml elements of the pom.xml,
and likewise it's also possible to do the reverse and adjust content of the pom.xml.

For more background information regarding properties, see https://maven.apache.org/pom.html#Properties


## Step 1: Use dedicated output folder per revision
We'll now arrange for every minor revision of our project to use a dedicated output folder,
so you can review the evolution of the contents in this folder during this workshop.

* Uncomment the `<directory>` element on line 18.

The xml element `project/build/directory` is the same as the Maven property `${project.build.directory}`.
According to the POM reference you saw earlier, it defaults to `${project.basedir}/target`
but we've now appended the revision to that.<br/><br/>
If you execute &nbsp; `mvn validate` &nbsp; again, you should see that reflected.
The properties derived from `project/build/directory` have changed as well.


# Step 2


## Step 2: Package the Source files into the .jar
After some warming up we're now finally going to get some things done: 
change the pom.xml so we can ship an artifact of our own design. 

We'll start with copying in the java source code in an unconventional way.

* Increase the `<revision>` on line 13.
* Duplicate the `<execution>` section on lines 25 thru 38
* Set the `<phase>` of the second `<execution>` to `prepare-package`
* Replace the four `<echo>` statements with:
````xml
<copy todir="${project.build.outputDirectory}">
    <fileset dir="${project.build.sourceDirectory}" />
</copy>
````

<small>ANT is asked to copy some files to the **outputDirectory**.
What is copied? The fileset residing in the **sourceDirectory**.
The `copy` task of ANT can be made quite complicated with many parameters.
<br/>See https://ant.apache.org/manual/Tasks/copy.html
</small>


## Step 2: Fixing the failing execution
If you test the change we made now, you'll see it fails:
````ini
INFO] Scanning for projects...
[ERROR] [ERROR] Some problems were encountered while processing the POMs:
[ERROR] 'build.plugins.plugin[org.apache.maven.plugins:maven-antrun-plugin].executions.execution.id' 
must be unique but found duplicate execution 
with id default @ nl.ordina.jtech:MavenDemo:0.${revision},
~/ordina-jtech/MetMavenKunJeAllesBouwen/pom.xml, line 39, column 32
 
````

<small>An execution block should have its own unique id.<br/>
Since none was supplied, it took **"default"** for both execution blocks.</small>

* Insert this line inbetween the first `<execution>` and `<phase>validate`
````xml
<id>step00</id>
````
* Do the same after `<phase>prepare-package`, but with id = `step01`
* Also change the name of the first `target` on line 32 to `step00`


## Step 2: Try it out
If you now execute &nbsp; `mvn clean install`, it will show the following 
inbetween running the unit test and building the .jar:

````ini
[INFO] --- maven-antrun-plugin:1.6:run (step01) @ MavenDemo ---
[INFO] Executing tasks

ant-step01:
     [copy] Copying 1 file to ~/ordina-jtech/MetMavenKunJeAllesBouwen/target2/classes
[INFO] Executed tasks

````

<small>If this completely fails (even after asking for help)
you can fetch a working solution from the `solution` branch.
There are [solution branches](https://github.com/Ordina-JTech/MetMavenKunJeAllesBouwen/branches/all)
provided for each step.<br/>
Just run the command:&nbsp; `git checkout solution/2 --force`</small>

* Explore the **workspace** folder `target2/classes`.
* Verify that the file `HelloMaven.java` has been copied there.
* Rename the file `target2/MavenDemo-0.2.jar` to `.zip` (yes, you can!)
  so you can see the file `HelloMaven.java` is actually in there as well.


## Step 2: Bonus - log the file names
You have seen in the console log just one file was copied. 
For debugging purposes it would be nice to see exactly which file was copied.

* Revisit https://ant.apache.org/manual/Tasks/copy.html 
  and see what extra parameter you can specify
  for logging the files that are being copied.

Can you implement that extra parameter?


# Step 3


## Step 3: No more "jar" packaging 
We'll definitely turn our back to the Java world.
After all it's not a .jar we want to produce, but something more generic like a .zip file.
You just saw that a .jar file can be treated exactly as a .zip file, but that would be cheating.

The reason the project currently produces a .jar is at line 10:
````xml
<packaging>jar</packaging>
````

You can try changing the packaging to `zip` but that will give an error:
````ini
[INFO] Scanning for projects...
[ERROR] [ERROR] Some problems were encountered while processing the POMs:
[ERROR] Unknown packaging: zip @ nl.ordina.jtech:MavenDemo:0.${revision}, 
~/ordina-jtech/MetMavenKunJeAllesBouwen/pom.xml, line 10, column 16
````


## Step 3: No more "jar" packaging 
On the [Maven POM reference page](https://maven.apache.org/pom.html#Maven_Coordinates)
there is a list of the valid values for the `<packaging>` element,
and `zip` is not one of them.

What we can (and will) do instead, is set the packaging to `pom`.

Maven will then only put the `pom.xml` file into the repository.
Maven produces a pom file for each and every packaging type, so this is to be expected.
You cannot publish a Maven artifact without also publishing its pom.

Question: have you seen Maven projects previously with `pom` packaging?<br/>
What was the purpose of that project?

* Increase the `<revision>` on line 13.
* Change the `<packaging` on line 10 to `pom`


## Step 3: Try it out
* Execute &nbsp; `mvn clean install`

<small>You'll notice the produced log is much smaller,
since only our two `antrun` tasks are executed.
If you look at the 
[Built-in Lifecycle Bindings](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html#Built-in_Lifecycle_Bindings),
and scroll down to section **Default Lifecycle Bindings - Packaging pom**,
you'll see that only the phases `package`, `install` and `deploy` are bound to a plugin to do some work.
Later on we'll bind some of our own tasks to some of the standard phases.
</small>

* Look at the folder `target3` in the project workspace.

<small>
The `target` folder of the previous run is still there, so you can compare the contents.
This `target` folder still contains a `classes` subfolder,
but it only contains `HelloMaven.java` and a temp `antrun` folder.
<br/><br/>
The file `HelloMaven.class` was produced by the `compiler:compile` goal,
and `readme.txt` was put there by the `resources:resources` goal.
Both these goals are part of the goal bindings for `jar` packaging and the likes,
but are not bound to `pom` packaging.
</small>


## Step 3: Comparing the produced artifacts
* Browse your **local Maven repository** and open the **MavenDemo** subfolder within your own **groupId**

Since we've incremented the revision with each build, 
we can compare what Maven has produced and published in the local repository.

In the folder for version 0.2 you'll find a .jar file, but in the 0.3 folder
you'll only find a renamed version of the pom and a file `_remote.repositories`
which Maven uses for internal purposes.


# Step 4


## Step 4: Creating a .zip file
How exactly should we create a .zip file?

<small>The naieve way could be to invoke WinZip.exe or 7Zip.exe.
But we'd like our build process to be able to run on all platforms,
like Windows, Linux variations and Apple OS/X.
So using a platform-specific executable is out of the question.<br/><br/>
Here ANT comes to the rescue once again, being platform-independant
and we'll implement an extra execution step that will create a zip file.</small>

* Look at https://ant.apache.org/manual/tasksoverview.html
  in the chapter **Archive Tasks**
  which ANT task you think is suitable for this.


## Step 4: Creating a .zip file with ANT
* Increase the `<revision>` on line 13.
* Duplicate the second `<execution>`
* Set the `<id>` of the new execution to `step02`
* Change the name of the `<target>` likewise
* Set the `<phase>` to `package`
* Replace the whole `<copy>` block with:

````xml
<zip basedir="${project.build.outputDirectory}"
     destfile="${project.build.directory}/payload.zip" />
````

We're telling ANT to zip the whole content of the `outputDirectory`.<br/>
The destination is the `project.build.directory` folder, which is `target4`.
The zip file name is `payload.zip`.


## Step 4: Try it out
* Run &nbsp; `mvn clean install`

<small>(Partial) expected output:</small>

````ini
[INFO] --- maven-antrun-plugin:1.6:run (step01) @ MavenDemo ---
[INFO] Executing tasks

ant-step01:
     [copy] Copying 1 file to ~/ordina-jtech/MetMavenKunJeAllesBouwen/target4/classes
[INFO] Executed tasks
[INFO] 
[INFO] --- maven-antrun-plugin:1.6:run (step02) @ MavenDemo ---
[INFO] Executing tasks

ant-step02:
      [zip] Building zip: ~/ordina-jtech/MetMavenKunJeAllesBouwen/target4/payload.zip
[INFO] Executed tasks
````

* Have a look at the content of the produced zip file.<br/>
  It should contain just one file.


# Step 5


## Step 5: Attach the zip file
We've made some good progress, since now a .zip file is produced instead of a .jar file.
What's still lacking is that Maven is not aware that along with the pom file,
our .zip file should be published to the repository.

* Increase the `<revision>` on line 13.
* Add the following line after the `<zip>` element:

````xml
<attachartifact file="${}.zip" />
````

We're telling Maven to **attach** a zip file to the list of produced **artifacts**.

* Fill in the correct expression for `file=${}.zip`.

Now Maven knows this attachment should also be published to the repository
during the `install` and `deploy` phases.


## Step 5: Try it out
* Execute &nbsp; `mvn clean install`

<small>(Partial) expected output:</small>

````ini
ant-step02:
      [zip] Building zip: ~/ordina-jtech/MetMavenKunJeAllesBouwen/target5/payload.zip
[INFO] Executed tasks
[INFO]
[INFO] --- maven-install-plugin:2.4:install (default-install) @ MavenDemo ---
[INFO] Installing ~/ordina-jtech/MetMavenKunJeAllesBouwen/pom.xml 
       to .m2/repository/nl/ordina/jtech/MavenDemo/0.5/MavenDemo-0.5.pom
[INFO] Installing /~/ordina-jtech/MetMavenKunJeAllesBouwen/target5/payload.zip
       to .m2/repository/nl/ordina/jtech/MavenDemo/0.5/MavenDemo-0.5.zip
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------

````

Note that the `attachartifact` task itself did not produce any logging.<br/>
It's only visible in the extra `Installing` log line.


## Step 5: Check the results
* Check your **local Maven repository** and verify there is a .zip file.

Note that Maven decided to rename our zip file to the artifactId plus the version.
This is the Maven convention of naming artifacts. 
This cannot be changed, otherwise consumers of the artifact 
would be unable to locate it in the repository.

There is one thing you can do, and that is to supply a **classifier** with the attachment.
For instance:

````xml
<attachartifact file="${project.build.directory}/payload.zip" classifier="foo"/>
```` 

will publish a file named `MavenDemo-0.5-foo.zip` to the repository.

You can attach multiple files only if they have different classifiers.


# Step 6


## Step 6: Using a different resource folder
So far we have zipped a `.java` file from the `main/src/java` folder.
The workspace also contains a `main/payload` folder with very different content,
and this is what we want to publish as an artifact.

**Source** files need to be compiled, while **resources** can be copied _as-is_.

Maven has a standard location for resources: the `src/main/resources` folder.
The location can be specified in the pom file with xml element
`project/build/resources/resource/directory`,
but in this project we'll take a somewhat different approach
and declare our own property for the resource location.

Why? Just because we can ;-)<br/>
<small>And it results in a smaller pom.xml</small>


## A side note about Resource Filtering
The standard Maven mechanism for packaging resources, is by using the
[maven-resource-plugin](http://maven.apache.org/plugins/maven-resources-plugin/index.html),
but for this project I have on purpose chosen not to.

A very good reason to _do_ use the `maven-resources-plugin`
is when the resources need _filtering_.
Maven has a different definition of what you'd intuitively expect of a filter.

Resource filtering does not deal with including or excluding certain files
(that is done with _includes/excludes_) 
but for _replacing property values_ during the build.
For instance, if you write `${project.version}` in a resource (like a html file)
then resource filtering will inject the current version there.

<small>For more information on resource filtering, see<br/>
https://maven.apache.org/guides/getting-started/index.html#How_do_I_filter_resource_files</small>


## Step 6: Use our own Resources folder
So we were about to declare a property for the location of our resources:

* Increase the `<revision>` on line 13.
* Add just below that a new property `<payloadDirectory>`
  and give it the location of the `main/payload` folder.
````xml
<payloadDirectory>${project.basedir}/main/payload</payloadDirectory>
````
* Add a line in the first Ant execution step to `<echo>` it's value
* Replace the source directory in the `<copy>` task with our new property
* You may edit or add any file or folder of your own in `main/payload`


## Step 6: Get rid of the "classes" folder
Since we're not building a Java project anymore,
we could get rid of the `classes` subfolder within the `target` directory.
Let's replace `classes` with something more generic, such as `bin`.

* Have a look at your console log which property holds the `classes` folder
* Amend the pom, so it uses `bin` instead of `classes`

<small>Hint: the xml element `project/build` starts in your pom.xml on line 19.</small>

* Execute &nbsp; `mvn clean install` &nbsp; once again
* Compare the produced .zip file with the contents of the `main/payload` folder of your workspace


## Mission accomplished, so
# Congratulations!
<br/><br/>
You have produced a .zip file with your own content as a Maven Artefact.


But wait... this was only step 6.

Wasn't there supposed to be 7 steps?


That's correct, there _are_ 7 steps!

But first, it time permits...


## Step 6 - Bonus: use the maven-resources-plugin
> Start only if you have more than 30 minutes left.

As mentioned three slides ago, you'd normally let Maven handle resources 
with the `maven-resources-plugin`.
To see how that differs from the ANT `copy` task we've used, we'll let you experiment with it.
Let it do some filtering e.g. by adding `${project.version}` somewhere in `readme.txt`

You can find some generic documentation here:
https://maven.apache.org/plugins/maven-resources-plugin/

<small>A fully written out **example** for inspiration can be found _somewhere_ in the left menu.</small>

### Good luck!


# Step 7
Finally...


## Step 7: Avoid code duplication
So now you know how to produce a Maven artifact with the content of your own liking,
it would be nice if you wouldn't need to write 75 lines of xml code again and again
for each project to accomplish that.

That's exactly what we're going to do, by creating a _parent_ pom file.

In our MavenDemo project we're going to refer to this parent project.
This parent project holds all the instructions how to build it,
and the child project only needs to supply the required properties.


## Step 7: Create the ZipParent pom
* Create a folder named `parent` in the workspace
* Copy the pom.xml file there from the root folder and edit it
* Set the artifactId to `ZipParent`
* Set the version to `1.0` and remove the now obsolete `revision` property
* Also remove the `<directory>` line (with the disclaimer)
* The first `<execution>` block with the `echo`es can be removed as well
* Execute on the command line:
````ini
cd parent
mvn install
````

This will fail. Can you think of a way to fix it?<br/>
<small><br/>Hint: For this parent project only the built pom file matters.<br/>
If you cannot stop that a .zip file is produced as well,
would its contents really matter?</small>


## Step 7: Reduce the size of the pom file
* Go back to the original **MavenDemo** pom file
* Increase the `<revision>` on line 13
* Remove the whole carefully crafted `<build>` block
* Insert a `<parent>` element instead
* Copy the three lines with `groupId`, `artifactId` and `version` from the just created parent project,
  and paste them within the `<parent>` element of our **MavenDemo** project.
* Try whether it builds on the command line:
````ini
cd ..
mvn clean install
````


# Congratulations!

Now whenever you want to pack some random resources as a Maven artifact,
just refer to your `ZipPom` parent, 
and let the `payloadDirectory` property point to the folder containing your files.


This is the end of the workshop.

I hope you can use some things you learnt today in practice.
<br/><br/>
> # Thank you.

![ordinalogo](img/ordina.png)
&nbsp;&nbsp;&nbsp;&nbsp;
![jtechlogo](img/jtech.jpg)

(c) 2018, Bert Koorengevel
