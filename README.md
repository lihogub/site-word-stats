# Analyze it!

![](https://user-images.githubusercontent.com/57266314/140565860-c428df4f-73d4-453d-b56a-e81f0d1754be.png)

Simple app to analyze site content.

## Features

- Written in Java 8
- No installation necessary - try it [online](http://sws.lihogub.ru)
- Easy to use
- Caches analyzed sites
- Multiplatform - run it with Docker
- [Jsoup](https://jsoup.org/) under the hood

## Installation

### Manual
```bash
$ git clone git@github.com:lihogub/site-word-stats.git
$ cd site-word-stats
$ mvn -f pom.xml clean
$ java -jar target/site-word-stats-0.0.1-SNAPSHOT.jar
```

### Docker
```bash
$ docker pull ghcr.io/lihogub/site-word-stats/site-word-stats:latest
$ docker run -d -p <YOUR_LOCAL_PORT>:8080 ghcr.io/lihogub/site-word-stats/site-word-stats
```

## Usage

| <img src="https://user-images.githubusercontent.com/57266314/140564692-fde2c221-4238-4682-a010-55ac36153d0e.gif" width="250"> | <img src="https://user-images.githubusercontent.com/57266314/140564702-abda62c2-d9ef-469b-b6ac-67245dad0526.gif" width="250"> | <img src="https://user-images.githubusercontent.com/57266314/140564753-7dd3a8c0-fa0c-4812-8262-0538ba4f3018.gif" width="250"> | <img src="https://user-images.githubusercontent.com/57266314/140564757-7040334f-8227-4d21-9860-ab32f9b008ba.gif" width="250"> |
|:--:|:--:|:--:|:--:|
| *Analyze site you want* | *Check word stats* | *See cached site stats* | Delete site from cache | |
