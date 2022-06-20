# Kubernetes Big Data Platform
쿠버네티스 환경에서 Hadoop/Spark 클러스터를 프로비저닝하고 공동 관리하는 MSA 기반 빅데이터 플랫폼

### Preview
![Untitled-3](https://user-images.githubusercontent.com/57346425/174521747-4fc7d20d-3f5f-4e74-8ba4-a4376fcc39fc.gif)

<br> 

## Introduction
컨테이너 기반 가상환경과 빅데이터 플랫폼이 주목받는 상황에서 대량의 데이터를 효율적으로 관리하는 것은 필수적이다. 하지만 빅데이터 분석을 위해 인프라를 구축하려면 많은 시간과 비용이 소요된다. 이러한 문제점을 해결하기 위해 전문지식이 없는 사용자도 웹 기반 인터페이스를 통해 컨테이너 오케스트레이션 환경에서 Hadoop/Spark 클러스터를 간편하게 구축하고 공유할 수 있는 플랫폼을 제공하고자 한다.

<br>

## Goal
<p align="center">
  <img src="https://user-images.githubusercontent.com/69456626/174544157-a9295558-1b82-4a70-b583-6983f5091124.png">
</p>

1. Kubernetes 환경에서 MSA 기반 빅데이터 플랫폼 구축
    - Apache Hadoop과 Spark 클러스터를 프로비저닝하고 공동 관리할 수 있는 웹 서비스 개발
    - 마이크로서비스는 독립적으로 배포가 가능한 각각의 기능을 수행하는 서비스이며, 본 시스템은 3개의 마이크로서비스(웹 플랫폼, 웹 서버, 쿠버네티스 제어 엔진)로 구성
2. 기존 시스템과의 차별성
    - 마이페이지에서 Hadoop/Spark 클러스터에 대해 타 사용자 초대 기능 제공
    - 사용자 초대를 통해 클러스터를 공동 관리하여 효과적인 협업을 지원

<br>

## System Architecture
<p align="center">
  <img src="https://user-images.githubusercontent.com/69456626/174543305-39536af8-f849-4620-8fbc-68ba504a00fd.png">
</p>

1. **Google Kubernetes Engine**
    - 본 과제에서 구현한 빅데이터 플랫폼은 관리형 쿠버네티스 엔진인 GKE(Google Kubernetes Engine)를 활용하여 클라우드 환경에서 구축함
    - 웹 프론트엔드, 웹 백엔드, 쿠버네티스 제어 엔진을 컨테이너화하여 각 노드에 배포
2. **웹 프론트엔드**
    - React로 개발된 웹 기반 인터페이스
    - 사용자가 빅데이터 클러스터 생성, 삭제, 수정, 사용자 초대 요청 가능
3. **웹 백엔드**
    - Spring Framework로 개발된 웹 서버
    - 프론트엔드의 요청을 받아서 쿠버네티스 제어 엔진으로 전달
    - RestTemplate를 통해 쿠버네티스 제어 엔진과 통신
4. **쿠버네티스 제어 엔진**
    - Spring Framework로 개발된 제어 엔진
    - 쿠버네티스(GKE) master node의 API Server로 접근
    - 사용자의 요청사항에 맞게 Hadoop/Spark 클러스터 구축 및 수정
    - 클러스터 생성 시 Hadoop/Spark docker image를 이용해 생성
    - 프론트엔드와 백엔드의 docker image를 이용해 쿠버네티스 내 pod로 배포

<br>

## Sequence Diagram
<p align="center">
  <img src="https://user-images.githubusercontent.com/69456626/174543433-da67c029-1a29-4af5-9afb-a77cd3a8b17c.png">
</p>

## Demo
https://youtu.be/SOw_VBL2TYo

## Member
||김도희|백혜원|이도윤|허현진|
|:---:|:---:|:---:|:---:|:---:|
|Role|Web-Frontend|Web-Backend|Kubernetes Control Engine|Kubernetes Control Engine|
|GitHub|[@doheez](https://github.com/doheez)|[@HyeW](https://github.com/HyeW)|[@idoburnish](https://github.com/idoburnish)|[@heohyeonjin](https://github.com/heohyeonjin)|
