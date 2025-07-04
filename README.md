# SPRING PLUS 13단계 조회성능개선
![img.png](images/img.png)
- 유저생성시간
- 조회 소요시간

이렇게 두가지로 나누어 성능개선 연습을 하였습니다.
자세한 내용은 [블로그(클릭)](https://ung9776.tistory.com/82)를 통해 확인할 수 있습니다.


# 유저생성
## Save -> SaveAll
### Save 사용
![img.png](images/save.png) ![img.png](images/img3.png)
### SaveAll 사용
![img.png](images/savAll.png) ![img.png](images/개선후유저더미생성.png)
---
# 조회
## 닉네임에 인덱스 부여
### 기존방식 type = ALL
![img.png](images/쿼리자동메서드.png) ![img.png](images/124124.png)
![img_3.png](images/쿼리문.png) ![img_2.png](images/인덱스사용하지않고조회.png)![img.png](images/개선전쿼리조회.png)
### 개선된 방식 type = ref
![img.png](images/인덱스로조회.png)![img.png](images/good!.png)

2배가량 속도가 빨랐다..!