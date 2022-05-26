# 어떤 이미지를 사용해서 빌드할 것인지 base image를 정의함
# node의 최신 LTS(장기 지원) 버전인 12을 사용
FROM node:12-alpine

# 이미지 안에 애플리케이션 코드를 넣기 위해 앱 디렉터리 생성
WORKDIR /usr/src/app

# 앱 의존성 설치
# 가능한 경우(npm@5+) package.json과 package-lock.json을 모두 복사하기 위해 와일드카드를 사용
COPY package*.json ./

RUN npm install

# 앱 소스 추가
COPY . .

# 앱이 3000포트에 바인딩되어 있으므로 EXPOSE 지시어를 사용해서 docker 데몬에 매핑함
EXPOSE 3000

# 앱 실행
CMD ["npm", "start"]