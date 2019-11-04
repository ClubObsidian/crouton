# crouton

[![Build Status](https://travis-ci.org/ClubObsidian/crouton.svg?branch=master)](https://travis-ci.org/ClubObsidian/crouton)
[![](https://jitpack.io/v/clubobsidian/crouton.svg)](https://jitpack.io/#clubobsidian/crouton)
[![Known Vulnerabilities](https://snyk.io/test/github/ClubObsidian/crouton/badge.svg?targetFile=build.gradle)](https://snyk.io/test/github/ClubObsidian/crouton?targetFile=build.gradle)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)


A wrapper for running Kotlin coroutines in Java code.

## Usage

Create a new Crouton.

`Crouton crouton = new Crouton();`

Then run one of the async methods.

If you want to stop a task call the `stop()` method in the `JobWrapper`.

## Setting up as a dependency

### Gradle

``` groovy
repositories {
	maven { url 'https://jitpack.io' }
}

compile 'com.github.clubobsidian:crouton:0.5.1'
```

### Maven

``` xml
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>

<dependency>
	<groupId>com.github.clubobsidian</groupId>
	<artifactId>crouton</artifactId>
	<version>0.5.1</version>
</dependency>
```
