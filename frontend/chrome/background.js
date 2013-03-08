var putToCloud = function(url) {
  if (url.substring(0, 4) != "http" && url.substring(0, 5) != "https") {
    return;
  }
  var xmlhttp = new XMLHttpRequest();
  xmlhttp.open('PUT', 'http://mindreading.heroku.com/voidmain/test?url=' + url);
  xmlhttp.send(null);
};

chrome.tabs.onUpdated.addListener(function (tabId, changeInfo, tab) {
  putToCloud(tab.url);
});
chrome.tabs.onActivated.addListener(function (activeInfo) {
  chrome.tabs.get(activeInfo.tabId, function(tab) {
    putToCloud(tab.url);
  });
});
