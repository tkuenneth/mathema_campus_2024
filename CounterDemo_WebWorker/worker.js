self.addEventListener("message", (event) => {
    const { start, end, delay } = event.data;
    let i = start + 1;
    postMessage(start);
    const intervalId = setInterval(() => {
        postMessage(i);
        if (i++ > end) {
          clearInterval(intervalId);
        }
      }, delay);
});
