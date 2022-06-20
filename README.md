# Kubernetes Big Data Platform
쿠버네티스 환경에서 Hadoop/Spark 클러스터를 프로비저닝하고 공동 관리하는 MSA 기반 빅데이터 플랫폼

- ✨2022년 한국정보기술학회 대학생논문경진대회 **금상 수상**✨<br>
- 2022년 1학기 경북대학교 종합설계프로젝트2<br>
- 산학협력기관: ㈜데이터스트림즈<br>

### Preview
<p align="center">
  <img src="https://user-images.githubusercontent.com/57346425/174558694-2fcbb8fc-d12d-4d76-9792-d906dbd3567a.gif">
  <em><b>[ Main Page ]</b> Creating Cluster</em>
</p>
<br>

<p align="center">
  <img src="https://user-images.githubusercontent.com/57346425/174566890-36832493-7c70-489e-ad4f-0af24535b1e4.gif">
  <em><b>[ My Page ]</b> Modify Cluster Amount / Delete Cluster / Invite User etc.</em>
</p>
<br>

<p align="center">
  <img src="https://user-images.githubusercontent.com/57346425/174570233-46ebf718-1967-472c-8db0-da6dee766aba.gif" width="60%" height="60%">
</p>
<p align="center">
    <em>Responsive Web</em>
</p>

<br><br>

## Introduction
컨테이너 기반 가상환경과 빅데이터 플랫폼이 주목받는 상황에서 대량의 데이터를 효율적으로 관리하는 것은 필수적이다. 하지만 빅데이터 분석을 위해 인프라를 구축하려면 많은 시간과 비용이 소요된다. 이러한 문제점을 해결하기 위해 전문지식이 없는 사용자도 웹 기반 인터페이스를 통해 컨테이너 오케스트레이션 환경에서 Hadoop/Spark 클러스터를 간편하게 구축하고 공유할 수 있는 플랫폼을 제공하고자 한다.

<br>

## Goal
![그림1](https://user-images.githubusercontent.com/57346425/174588105-1b6392ae-aa78-403d-b9a2-32484a75acd4.png)

1. Kubernetes 환경에서 MSA 기반 빅데이터 플랫폼 구축
    - Apache Hadoop과 Spark 클러스터를 프로비저닝하고 공동 관리할 수 있는 웹 서비스 개발
    - 마이크로서비스는 독립적으로 배포가 가능한 각각의 기능을 수행하는 서비스이며, 본 시스템은 3개의 마이크로서비스(웹 플랫폼, 웹 서버, 쿠버네티스 제어 엔진)로 구성
2. 기존 시스템과의 차별성
    - 마이페이지에서 Hadoop/Spark 클러스터에 대해 타 사용자 초대 기능 제공
    - 사용자 초대를 통해 클러스터를 공동 관리하여 효과적인 협업을 지원

<br>

## System Architecture
![그림2](https://user-images.githubusercontent.com/57346425/174599516-e684f3f2-0ff1-463e-a7d6-e3a715c2100b.png)

1. **Google Kubernetes Engine**
    - 본 과제에서 구현한 빅데이터 플랫폼은 관리형 쿠버네티스 엔진인 GKE(Google Kubernetes Engine)를 활용하여 클라우드 환경에서 구축함
    - 웹 프론트엔드, 웹 백엔드, 쿠버네티스 제어 엔진을 컨테이너화하여 각 노드에 배포
2. **웹 프론트엔드**
    - React로 개발된 웹 기반 인터페이스
    - Hadoop/Spark 클러스터 생성, 삭제, 수정, 사용자 초대 요청 가능
    - 쿠버네티스 파드 정보를 실시간으로 조회
    - 다양한 디바이스에 최적화된 사용자 중심적 반응형 웹 페이지
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
![그림3](https://user-images.githubusercontent.com/57346425/174599752-04f796af-f3b4-4b95-ba51-bdc207c77e74.png)

<br>

## Implementation Technology

#### [클러스터 구성 및 관리]
![그림4](https://user-images.githubusercontent.com/69456626/174616554-e2438e1b-a308-41f9-9f93-a156433560ae.png)
- Operator와 Custom Resource를 통해 Hadoop 및 Spark 클러스터 구축 과정과 관리를 자동화하도록 구현
- Custom Resource Definition(CRD)를 통해 Operator가 관리해야 할 Hadoop Cluster와 Spark Cluster에 대한 규정(Spec)을 정의하여 쿠버네티스에 등록
- 쿠버네티스 제어 엔진을 통해 앞서 규정한 CRD의 구현체인 Custom Resource(CR) 생성을 요청
- CR에는 Hadoop 및 Spark Cluster가 유지해야 할 clusterSize에 대한 정보가 포함
- Operator가 자동적으로 clusterSize를 모니터링하여, 클러스터 크기를 지정된 숫자만큼 유지

#### [클러스터 공동 관리]
![그림5](https://user-images.githubusercontent.com/69456626/174619239-dd449155-d8de-4652-a5df-f81193d71572.png)
- 각 클러스터별 사용자 초대 기능을 도입하여 여러 사용자들은 하나의 클러스터를 공동 관리 가능
- User-Cluster를 다대다 관계로 설정하여, 사용자는 다수의 클러스터를 생성 가능하고 클러스터는 다수의 사용자가 관리 가능
- 여러 사용자가 하나의 클러스터를 프로비저닝함으로써, 훨씬 더 유연하고 확장된 클러스터 관리 환경을 제공

<br>

## Expectation Effectiveness
- Kubernetes 환경에서 빅데이터 플랫폼을 구성하고 관리할 수 있는 기술을 개발함으로써 현시대의 데이터 폭증에 대응할 수 있는 서비스를 제공할 수 있을 것으로 기대
- 온프레미스 환경에서 빅데이터 분석 환경 구축 시 발생하는 비효율적인 서버 사용률 문제점 해소
- 전문지식이 없는 사용자도 웹 기반 인터페이스를 통해 간편하게 컨테이너 오케스트레이션 환경에서 빅데이터 클러스터 구축 가능
- 여러 사용자가 빅데이터 분석 환경을 공유하여 공동 관리가 가능한 플랫폼을 제안

<br>

## Application Plan
![그림6](https://user-images.githubusercontent.com/69456626/174621334-1a38da88-4844-4ae1-adc4-57f8b6348548.png)
- 빅데이터를 사용하는 프로그램에서 활용 가능 (ex. 잃어버린 반려동물 찾기 웹 페이지)
    - 예시 웹 페이지는 본 과제에서 개발한 빅데이터 플랫폼을 활용하여 Hadoop 클러스터 생성 및 HDFS에 전체 유기견 데이터를 저장
    - 인메모리 환경에서 실시간으로 데이터 분석 기능을 제공하는 Spark 클러스터를 생성해 유기 동물 검색과 같은 빅데이터 분석 기능 구현
- 빅데이터 플랫폼 종류를 Hadoop, Spark에서 나아가 Hive, Kafka 등 다양한 빅데이터 플랫폼을 추가하여 더욱 다양한 서비스 개발 가능
<br>

## Demo
https://youtu.be/SOw_VBL2TYo

<br>

## Member
||김도희|백혜원|이도윤|허현진|
|:---:|:---:|:---:|:---:|:---:|
|Role|Web-Frontend|Web-Backend|Kubernetes Control Engine|Kubernetes Control Engine|
|GitHub|[@doheez](https://github.com/doheez)|[@HyeW](https://github.com/HyeW)|[@idoburnish](https://github.com/idoburnish)|[@heohyeonjin](https://github.com/heohyeonjin)|

