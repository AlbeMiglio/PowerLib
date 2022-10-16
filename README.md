# PowerLib
[![Build Status](https://travis-ci.com/AlbeMiglio/PowerLib.svg?branch=master)](https://travis-ci.com/AlbeMiglio/PowerLib)
![GitHub](https://img.shields.io/github/license/AlbeMiglio/PowerLib?color=bright-green&label=License)
![GitHub All Releases](https://img.shields.io/github/downloads/AlbeMiglio/PowerLib/total?color=brightgreen&label=Downloads)
[![Discord](https://img.shields.io/discord/618742870035398684?logo=Join%20on%20Discord&label=Discord)](https://discord.gg/UMhsCZk)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=AlbeMiglio_PowerLib&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=AlbeMiglio_PowerLib)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=AlbeMiglio_PowerLib&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=AlbeMiglio_PowerLib)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=AlbeMiglio_PowerLib&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=AlbeMiglio_PowerLib)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=AlbeMiglio_PowerLib&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=AlbeMiglio_PowerLib)

PowerLib is a server-side user interface library for Spigot, BungeeCord and Velocity servers.
## Importing with Maven/Gradle
- To hook this library into your project with Maven, you just need to add to your pom.xml the repositories and dependencies below:
```
	<repositories>
		<repository>
                    <id>codemc-repo</id>
                    <url>https://repo.codemc.org/repository/maven-public/</url>
                </repository>
	</repositories>

	<dependencies>
	    <dependency>
  		<groupId>it.mycraft</groupId>
  		<artifactId>powerlib-<YOUR-PLATFORM></artifactId>
  		<version>1.2.1</version>
		<scope>provided</scope>
	    </dependency>
	</dependencies>
```
Replace the `<YOUR-PLATFORM>` string with the platform you're going to use PowerLib for.

- Bukkit/Spigot/Paper (& forks): `bukkit`
- BungeeCord: `bungee`
- VelocityPowered: `velocity`

In case of an all-in-one application, there is an experimental option
to include all platforms in just one dependency. Use it at your own
risk, and don't do that unless you know what you're doing!
- (Experimental) ALL: `all`

If you're using Gradle, just put the repositories and dependencies below into your `build.gradle`. As said before, replace `<YOUR-PLATFORM>` with
your platform between the ones expressed above.

```
repositories {
    maven {
        url 'https://repo.codemc.org/repository/maven-public/'
    }
    mavenCentral()
}

dependencies {
    compileOnly 'it.mycraft:powerlib-<YOUR-PLATFORM>:1.2.1'
}
```

Please DO NOT SHADE PowerLib onto your projects, since it will no longer
be possible soon due to some instances registrations.
