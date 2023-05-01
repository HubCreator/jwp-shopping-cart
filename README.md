# 🛒 장바구니 웹 구현 미션

## Pair: 져니 [⛄️](http://github.com/cl8d), 헙크 [🫠](https://github.com/HubCreator)

## ✔ 1단계 기능 요구사항

- [x] 상품 목록 페이지 연동
  - [x] `/` url로 매핑
  - [x] 도메인 객체 설계
  - [x] 뷰 렌더링 구현
- [x] 상품 관리 CRUD API 작성
  - [x] 생성
  - [x] 단일 조회
  - [x] 리스트 조회
  - [x] 수정
  - [x] 삭제
- [x] 관리자 도구 페이지 연동
  - [x] `admin` url로 매핑 (등록)
  - [x] `admin` url로 매핑 (조회)
  - [x] `admin` url로 매핑 (수정)
  - [x] `admin` url로 매핑 (삭제)
  - [x] 뷰 렌더링 구현
- [x] DB 연동
  - [x] 테이블 구조 설계

## ✔ 2단계 기능 요구사항
- [x] 사용자(User) 기능 구현
  - [x] email과 password를 가짐
- [x] 사용자 설정 페이지 연동
  - [x] settings.html 파일을 수정
  - [x] `/settings` url로 접근할 경우 모든 사용자의 정보를 확인하고 사용자를 선택할 수 있다.
  - [x] 이 페이지에서 사용자를 선택하면, 이후 요청에 선택한 사용자의 인증 정보가 포함된다.
- [ ] 장바구니 기능 구현
  - [x] 장바구니에 상품 추가
  - [ ] 장바구니에 담긴 상품 제거
  - [ ] 장바구니 목록 조회
  - [ ] 사용자 정보는 Header의 `Authorization` 필드를 사용해 인증 처리한다. 인증 처리는 Basic 방식으로 처리한다.
- [ ] 장바구니 페이지 연동
  - [ ] `index` 페이지에서 담기 버튼을 통해 상품을 장바구니에 추가할 수 있다.
  - [ ] `/cart`로 접근할 경우 장바구니 페이지를 조회할 수 있다.
  - [ ] 장바구니 목록을 확인하고 상품을 제거하는 기능을 동작하게 만든다.
