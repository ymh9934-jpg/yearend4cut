/**
 * 연말네컷 사진 올리기 서버 (Google Apps Script)
 *
 * 탭 앱이 완성된 네컷 사진을 보내면 내 구글 드라이브의 '연말네컷 사진' 폴더에 저장하고,
 * 링크가 있는 사람은 누구나 볼 수 있게 공유한 뒤 파일 id를 돌려줘요.
 * 앱은 그 id로 QR코드를 만들어 보여 주고, 사람들은 QR을 찍어 사진을 받아 가요.
 *
 * 배포 방법
 * 1. script.google.com → 새 프로젝트 → 이 코드를 전부 붙여 넣고 저장
 * 2. 오른쪽 위 '배포' → '새 배포' → 유형: 웹 앱
 *    - 실행: 나
 *    - 액세스 권한: 모든 사용자
 * 3. 권한 허용 후 나오는 웹 앱 URL(https://script.google.com/macros/s/.../exec)을 복사해
 *    앱 첫 화면 맨 아래 '사진 QR 설정'에 붙여 넣어요.
 *
 * 개인 Gmail 계정으로 배포해 주세요. 회사·교회 워크스페이스 계정은 외부 공유가 막혀 있을 수 있어요.
 */

const FOLDER_NAME = '연말네컷 사진';

function doPost(e) {
  try {
    const body = JSON.parse(e.postData.contents);
    if (!body.data) return out_({ok: false, error: '사진이 비어 있어요'});
    const bytes = Utilities.base64Decode(body.data);
    const name = String(body.name || ('연말네컷_' + Date.now() + '.jpg')).slice(0, 120);
    const blob = Utilities.newBlob(bytes, 'image/jpeg', name);
    const file = folder_().createFile(blob);
    file.setSharing(DriveApp.Access.ANYONE_WITH_LINK, DriveApp.Permission.VIEW);
    return out_({ok: true, id: file.getId()});
  } catch (err) {
    return out_({ok: false, error: String(err && err.message || err)});
  }
}

// 브라우저로 주소를 열어 보면 연결 확인용 메시지가 나와요.
function doGet() {
  return out_({ok: true, app: '연말네컷', folder: FOLDER_NAME});
}

function folder_() {
  const it = DriveApp.getFoldersByName(FOLDER_NAME);
  return it.hasNext() ? it.next() : DriveApp.createFolder(FOLDER_NAME);
}

function out_(obj) {
  return ContentService.createTextOutput(JSON.stringify(obj)).setMimeType(ContentService.MimeType.JSON);
}
