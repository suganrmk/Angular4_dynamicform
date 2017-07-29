import { PTDPage } from './app.po';

describe('ptd App', () => {
  let page: PTDPage;

  beforeEach(() => {
    page = new PTDPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
