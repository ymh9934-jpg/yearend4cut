# 연말네컷

갤럭시 폰으로 찍는 네컷 사진 촬영기예요. 타이머에 맞춰 네 번 찍으면 프레임과 글씨가 들어간 한 장이 완성되고, 갤러리의 `연말네컷` 앨범에 저장하거나 카톡으로 바로 보낼 수 있어요.

## 앱 받기 (갤럭시)

1. 휴대폰에서 아래 주소를 열면 최신 앱 파일이 내려받아져요.
   `https://github.com/ymh9934-jpg/yearend4cut/releases/latest/download/yearend4cut.apk`
2. 내려받은 `yearend4cut.apk` 를 누르고 **설치**를 눌러 주세요. 처음 한 번은 "출처를 알 수 없는 앱" 설치를 허용해야 해요.
3. 앱에서 처음 촬영할 때 카메라 권한을 허용해 주세요.

## 기능

- 모양: 세로 4컷(1200×3690) / 2×2 네모(1200×1800, 4×6 인화 비율)
- 프레임: 눈 오는 밤, 크리스마스, 샴페인, 핑크 파티, 화이트, 블랙
- 필터: 기본, 흑백, 따뜻하게, 뽀샤시 (찍은 뒤에도 바꿀 수 있어요)
- 타이머 3·5·10초, 앞/뒤 카메라 전환, 마음에 안 드는 컷만 다시 찍기
- 사진 아래 글씨와 날짜 자동 입력

## 처음 한 번만: 서명 비밀번호 넣기

저장소 Settings → Secrets and variables → Actions → New repository secret 에서
이름 `SIGNING_PASSWORD`, 값은 따로 전달받은 비밀번호로 추가해요. 이게 있어야 APK가 만들어져요.

## 업데이트

`www/index.html` 을 고쳐서 main 브랜치에 올리면 GitHub Actions가 새 APK를 만들어 Releases에 올려요. 같은 주소에서 다시 받아 설치하면 덮어쓰기로 업데이트돼요.

## 구조

- `www/index.html` : 앱 화면 전체 (한 파일)
- `android/` : Capacitor 안드로이드 프로젝트
- `android/app/src/main/java/kr/yearend4cut/app/PhotoSaver.java` : 갤러리 저장·공유
