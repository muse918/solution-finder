package _usecase.util.fig;

import _usecase.FigureFileHelper;
import _usecase.Log;
import _usecase.RunnerHelper;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import entry.EntryPointMain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class UtilFigTetfuCaseTest extends UtilFigUseCaseBaseTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();

    @Override
    @BeforeEach
    void setUp() throws IOException {
        super.setUp();
    }

    @Test
    void useTetfuCase1() throws Exception {
        // オプションなし

        String tetfu = "v115@vhKSSYDEFLDmClcJSAVDEHBEooRBMoAVBzvKxCae/w?CauKWCzO8LCTnzPC0SNFDK3LMCU9aPC6ibMCU3TWCqyaFDT?+lPC6iHgCze9VCUeLuCK3ytCpXExCqvKxC6yLMCTe/VCvuz?PCPtjxCpiHgC6+TWCpirgCKuTxCTn/wCa+DWCa9aPCp+CMC?zuzPCsHDMCs+jPC0XMgCpvTxCM9aFDKO9VCz/dgC6yjFDpu?HgCvCmPCadNPCznFgC0CmFDMdFgCqyjFDPe/wCUdNPC6yKW?Cae/wCPNWWCKXNFDTNmPC6SFgCU9aFDK+DxCvvjFDpSNPCT?HmFDMefBAXqBznBGjBNpB0fBRmBesBMrBlqBTpBzgRpHeRp?IeI8wSwwH8wSwwH8JeSjBvhNxgBfZBybBFdB3bBzcBsaBWX?B9eBZkBysBskBWnBpeBegwhIewhBeAtBeg0EeBtg0Aei0gl?BeAtAeh0AeilAtCewhQ4xSwhQpAtA8APAtili0QpA8APAtx?hxwwhRpJefXBvhBzTBdTBpfwwBewhCeQ4AexwRpwhBeAtR4?g0wwRpwhAeBtg0Q4i0glwhAeBti0ilwwAeBtR4QpgWQ4wwA?tA8AeA8whA8RpxwQ4A8AeAtxhglQpxwQ4AeBtglwhilg0Q4?AeBtili0QpAeBtxhxwwhRpAAA8HeAAA8ReXxAvhETrA+nAM?0AZBBKBBweg0Ieg0FeRph0glDeAtRpR4glCeBtwwR4whhlA?eQ4AtxwRpQLwhAeAtR4g0wwRpQLA8AeD8xwhlg0AeC8Atxw?xhgWC8BtQpxhQ4gWg0A8whAtRpxwQ4QaA8AtxhglQpxwQ4A?AA8HeAAA8HeA8EeAACeA8EeAACeA8EeAACeA8EeAACeA8Ee?AAMecpAvhF5yAz3AF5Ay6AO8A3+AAfR4Aegli0BeR4ilg0A?eI8AeI8AeI8AeN8whwDA8g0ilB8xhgWh0glA8EeAACeA8Ee?AACeA8EeAACeA8FeAAHeA8AAHeA8AAEeAABeA8FeAABeA8C?eAAEeA8MeTfAvhICiAcjAflAZtAmyA00Ai6A97AT3AZfRpG?ewwRpFezwD8AeB8QpxwD8AeA8RpZeA8AAHeA8AAOeAACeA8?EeAACeA8BeAABeA8HeAAA8FeA8AeAAQe2zAPfRpg0FewwCe?h0CewwAexwilA8AeA8RpjeA8AAYeAACeA8LeAABeA8FeA8A?eAAkevqAvhIJ6A6JBlOB5RBNaB0cBWjBTZBPYBQgQ4IeR4A?eRpwwCeAtwSQ4g0RpxwAewhQ4xSg0SpA8AeA8BtwhglxwRp?AeQ4whBtilLeA8BeAAFeA8BeAANezHBvhI/JBUFBJbBiaBV?lBGeBTUBWhBcWBOgAtDeRpBeBtDeRpBeAtglCeg0RpBeAPg?lh0Q4AtwhQpglAewhwSQpR4whxSC8hlgHwhBtglg0A8Qpxw?wDxhxwEeA8BeAAFeA8BeAAKelVBvhEKVBvcBJYBpcBfcBYg?Q4IeR4xhCeglBewSQ4xhAeRpglAeAtwSDeRpg0B8AtwhR4A?8wSwwg0A8BtwhR4A8wSwwCeAADeA8DeAADeA8KeuYBvhA6P?BJghlCeQ4DegWEeQ4xhh0AeglBewSDewSwhBeAtGeg0A8Bt?whR4glwSwwMeAADeA8NeA8DeAAKe1JB+fwwhlCeQ4BewwAe?QpEeQ4whAPxhAeglBewSDewSwhBeAtGeg0A8BtwhR4glwSw?wMeAADeA8NeA8DeAAHeAAA8KeT+AvhQsLBTLBmLB8MBPOBF?UByVBmYB5bBTfB9jB3iBUhBKaBagB0hBpeBogwhJeglJehl?AeBtDei0A8BtB8AeQ4GeAAA8BeAAEeA8LeGbBAgwhIewhgl?i0Eewhilg0BtCewhH8AeQ4G8AeA8Q4g0gHhlE8Q4i0glBtA?8AeA8Q4GeAAA8LeA8EeAALeJKBvhE/TBTSB/TBlRBGNBOgi?0FewwRaAeQ4BewhBeQLAegWwSAeQ4whAeA8AeA8QpxwxhR4?AeA8EeAAmeA8AAKelQBvhNMUBTQBKGBaMB5iBvnBTmBulBp?eB8nBzaBVcB3RBldBYgR4GeQ4AegWDewhAeQpAPgWAezwAe?glBexhRLAeQaBeRaAewSQpAeQaHeQ4AeAPQpi0glxwwhAtA?eAtHeA8AAJeUOBvhBZLBSUBBgwhHeglwhBeR4BtilBeR4ww?AeBtAeQawhRpRaBPQpglQawhglQLQagWQpAPAeAtBeRaAew?SQpQ4QaAPQpi0glxwA8AtAPQ4i0glxwwhAtA8APCeAADeA8?DeAAEeA8JesGBvhAmJBBgwhAeAtCei0glAeAtQpQ4wwAehW?AeQawhQaAPQawhAeAPwhgWBeRaAewSQpAeQaHeQ4AeAPQpi?0glxwwhAtAeAtHeA8AACeAAEeA8MeA8EeAAJepDBvhCzCBl?BByBBvfglGehlHeUpwhDewShWRpBewwAtwhAeRaAewSglAe?QaHeQ4AeAPQpi0glxwwhAtAeAtHeA8AACeAAEeA8MeA8EeA?AEeAACeA8JeOJBvhCVIBUEB//ALfQ4IeR4DeglAewwBtQ4B?eilywBtBeywRpwhQag0Beilg0RpAewSAeAtB8g0A8QpBtwh?AeA8i0SpBtA8AeSpwSwwQ4hlB8i0gHxwwhAtA8AtHeA8AAE?eAACeA8AABeA8FeAABeA8FeAAHeA8AADeA8NeToAvhJCqA+?2AJ7AvFBdCBvFBTDBcBBm+A5/AqfwhCeAtBei0AeQ4AeAtA?ewwAexSCeQ4AewhDewhCeAtRpAexwwhQ4xhMeA8EeAAEeAA?CeA8AADeA8heA8AARe5CBvhB6PB3MBmfR4BewhhlAeAtR4i?0whQ4glE8AeD8AeI8AeI8AeB8xhB8Q4h0AeAtxhilQ4whg0?AeAACeA8DeA8CeAAEeA8CeAAEeA8CeAAEeA8CeAAFeA8BeA?AOeiyAvhCt5A+7AU6Aifg0EeBtHewhAeAtg0BeD8QpBthld?eA8AAIeA8BeAAueAAAeA8OezwAWfRpg0EeBtHewhAewhQpB?eD8QpBthldeA8AAIeA8BeAAueAAAeA8GeA8EeAAKe/uAvhJ?avAZ8AtGBOGBzIBTGBSPBUEB2MBMKB2fAtEeBtAeBtRpg0C?eCtgWD8AeBtA8BtxwglA8AeA8Ctg0feAAAeA8IeA8CeAACe?A8AAIeAADeA8Ke/6AvhSFDBZSBmaBXcBxfBTVBVcBcWBSYB?FeBMjBymBfjBzfBOfBpeB3hBKVBUYBLgglIeglDeBtBewhA?eglDeBtAewhAtg0CeRpQ4BeAtwhQpAeR4gWQpQ4AehlA8Ae?A8yhA8Q4glxwAexhg0xhQ4CeA8CeAAEeA8CeAALeJPBvhPG?JB9FBT/A/WBFXBOXBMKBKIBzVByQB5bBsaBOaBtXBTLBnYB?BgRpHeRpCewwDeRpAtAeglxwR4AtQpwhAtAeglAeR4BtAeh?WBewSgWAPwSwhAPhWAeAtwhBPxhglAtg0A8h0wwwSAtQpi0?A8hlwwwSRpAAFeA8BeAAFeA8Le5RBvhBybB+RB6fg0Ieg0F?eRph0glEeRpglAPglwwDeC8glC8AeB8xwhlg0B8AeB8wSww?i0QpD8AAFeA8BeAAFeA8LeA8BeAAPeTKBvhAdJBwfg0Leww?CeRpg0AeglwwBeRpBeBPFexwi0RpAexwJeAAFeA8feA8BeA?AIeA8DeAAKe5JBvhJ3NBfIB0DB+PB8OBCFBZaB1kBTUBfgB?dgRpKeQ4AeQ4BtAeglRaBeQ4AeQpAeAtAeglAtAegWAeQaA?eRpglBeA8QpwhgHyhBtglCeA8DeAADeAADeA8KeFTBvhFMN?BTJBZVB+QBdbBTUBMgg0FeRpAeg0CeAtTpg0AewwAeBtRpB?ewhQawwAeAtwhhlBehlQpAeBtzwQ4RpA8AtSpwSwwWeA8De?AAKeOGBjfh0Heh0FeRph0CeAtTpBewwAeBtTpQLQawwAeAt?ywBehlA8AeA8AtzwhlQpA8BtxwwSwwQ4RpAeAtSpxwCeA8D?eAAXeAADeA8DeAADeA8KeK4AvhEM5A5CBKJBfPB2LBxeg0I?ei0CeglQ4Beh0glBeAtglR4AeC8AeN8AeI8AeD8AeI8AeI8?AeF8glE8AeC8ilC8gWwhB8hlg0A8AeAtg0xhA8CeA8AAIeA?ACeA8IeA8AACeA8EeAACeA8EeAACeA8EeAAJepdAC8AeI8A?eL8AeI8AeG8AeD8Q4glC8AeD8Q4ilA8AeA8g0whA8Q4hlg0?B8Atg0xhQaCeA8EeAAHeA8AAHeA8AACeA8AAHeA8AAHeA8A?AIeAAAeA8GeAAAeA8ieAAFeA8BeAAFeA8BeAAFeA8BeAAFe?A8JeAAA";

        String command = String.format("util fig -t %s", tetfu);
        RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

        ByteSource actual = FigureFileHelper.loadGifByteSource();
        ByteSource expect = FigureFileHelper.loadResourceByteSource(UtilFigResources.TetfuCase1);

        assertThat(actual.contentEquals(expect)).isTrue();
    }

    @Test
    void useTetfuCase2() throws Exception {
        // オプション: next, delay, loop, hold

        String tetfu = "v115@vhHRQYLFFLDmClcJSAVDEHBEooRBJoAVBq+jFDM3KW?CaujPC0/DxCznFgCpCmFDzvaFDK+VWCPNmFDpintCvvjFDJ?HExCpurgCziPPCvyjFDpu3LCviLuC0HztCpOUPCaX9VCUt/?VCzurgCK+NMCsvaFDTnLuC0SNPCMNOMCanntCauaPC0i3LC?UNUFDv+bgCqX8LCTXNPC0izFDpCegCzfLuCp/DxCvfjxCpy?bgCzirgCK+dgCs/9tCaezPCJNegCsCegC6PltCvX8LCq+Tx?CqybgCpHztCpizPC0+LMCv3/VCv/9tCpXEWCp/dgCzXMgC0?vjFDJn/VCT+lFDpuntCMnbMC6OUPCP9KxCaujPCMnzPCJNW?WCzfjxCJHstCK+TFDU3LMC0fjWCv3/wCJNMgCpyytC03PFD?zSdBA+tB3mBcqBihBTpBlsBKpBfgglIeglCeilCehlAeAtg?lR4Beg0B8AeG8g0A8AeG8g0A8AeA8i0C8h0AeAtg0xhB8gl?JeMZBvhCRSB/LBuYBZfQ4IeR4HeglQ4Aezhh0AtglCeilg0?BthlAeAtglR4g0Atg0whA8AeG8xhAeG8g0whAeT4hlAtg0A?8AeA8i0glBth0A8APg0xhglAtglBeA8AAHeA8AAHeA8AAHe?A8AAPeTxAvhEepAd9Al9ASEBX/Alfg0MeR4BeQ4g0CeQ4Ae?QLRpwhglgHB8xhg0xwfeA8AAweA8AAOeU2Abfg0DeBtGeQ4?QpAeAtQ4g0CeQ4AeQLRawhglgHB8xhg0xwfeA8AAweA8AAI?eA8BeAALeTtAxeRpg0DeBtAeRpg0CeR4BtQ4h0BeR4glRpB?8AeG8xwgHD8BtA8xwgHC8xhBtwhglgHB8xhg0xwBeA8AAHe?A8AAHeA8AAHeA8AASeA8AAHeA8CeAABeAABeA8FeAABeA8F?eAACeA8EeAAFeA8LeRbAvhX6YAFgA8fAJuA23A/tATuAG1A?00AF2Ai1A5HB/MBNSB8MBpPBqNBuQBvTBTHBOQBfPBMUBTQ?BFfRpGeAtTpCeQ4BtG8AeB8AeI8AeI8AeI8AeO8AeE8AeG8?xwAeF8AtxwwSwwC8whBtBeAADeA8BeA8AeAAGeA8AeAAkez?lAxeRpCeRpBeAtTpAeRpQ4BtxwC8xwAeA8AtwSywA8xwwhB?tAAFeA8VeA8EeAACeA8AeAAIeAACeA8qeAAAeA8GeAAAeA8?GeA8HeAAA8HeAAJeZWAvhMthAKfAZtAd6AT7As0AC2Au4A+?sAT3AK3Af0A81A0eQ4DeAtg0glBeR4BeBtg0glCeQ4AeBth?0hlAeh0BtilB8AewhD8Atglg0A8AexhB8Btglg0A8AeA8wh?A8Bthlh0AehlBti0TeAAAeA8GeAAAeA8IeA8FeAABeA8FeA?AA8DeAADeA8DeAAIeAACeA8EeAACeA8JeJ9AvhJF4Au5AJ9?A/BBCABT2AX4AF+AU5AT3AZfRpAeBtCeTpR4BtBeRpBtQ4x?hwwAeglSaAtgHAeQpglAeAtwhQpwDxhSpA8i0gWwhQ4xhgl?A8BtglA8HeAAA8HeAAEeAACeA8EeAACeA8YeA8AeAAGeA8A?eAALeM1AvhSP6AN4AZ8A6PBuLBTOBUKBiBBFNBpcBGjBymB?3cB3VBMQBTZBGYBlbBxcBggAtHeAtCezhCeQpQ4AeQaywRp?gWRaAeglBPgWwhQpg0hlA8i0APAtglLeAADeA8LeZGBvhOa?RBeCBTUBtRB3TBsSBzIBvUBFSBKGBpKBuTB8bBXXBtaBBgg?lwwGewhglxwAeR4Cexhwwg0AeAtRpg0Q4whG8AeB8g0QpE8?AeA8Q4g0QpQLA8xhC8R4QpglA8APxwglwhQ4DeAABeA8FeA?ABeA8DeA8AeAAOe6HBvhF8OBxNBmNBTLB8TB3GBafR4GeR4?HeRpFeAtAeRpBei0BtAeB8AeH8xhA8AeE8xhB8AeE8xwB8A?eC8AtA8xwB8gHhlBtA8BeA8CeAAGeA8AeAAGeA8AeAAGeA8?AeAAGeA8AeAAMep2AvhCGpAN9AT/Aafi0GexhFewhQ4AewD?gHBeRpBeBPwwEeAtAexwQpA8AeA8xwAtQ4heA8AeAAweA8B?eAAJeOtAvhBZIBKyAneglIeglIehlHeh0Hej0FeAtR4g0Ee?whwwQ4DeRpAeyhwwCeRpAtwhhlD8AeC8jlB8AeC8glxhglB?8AeB8Q4xhD8wSwwA8Q4xwQpC8wSwwAtQ4FeA8BeAACeAABe?A8FeAABeA8FeAABeA8FeAABeA8FeAAEeA8JefxAvhClzAMm?ATnAdeglTeglEeAtBeBtDeAtAeRpBeh0CeQpCexhCeAPAeA?tgWwhAegWQawwAeQpAPBeBPBeQLCewSAexwRpB8wSwwAtQ4?teA8BeAACeAAEeA87elqAvhSKvATnAuvA3uAcuAZEB6BBTD?B3EBOJBpKBaMB8RBsSBdUBiQB5lB9jBviBvgQ4BewwGeQ4w?wAeilCewSGeBtAegWAeAPi0AeCtRpQ4CeA8BeAAMe+cBagg?0Q4BewwEeg0R4xwilAeg0AewSQ4wwAeilAeAtgWwSgWAeAP?i0AehlAtwhRpi0A8BtAPRpQ4MeAABeA8MezJBvhC3LBpABc?GBsfwhKeAtHeAtFeRpBeQpQ4Beg0Q4BeAtQpAewDDeAtglA?eRLglAeg0AewSGehWAegWAeAPi0AeCtRpQ4CeA8BeAAFeAA?BeA8MeAABeA8Pei/AvhKlMBuWB2bB0RBNQBzOB6XBJYB/aB?vcBSeBfgQ4CeQ4DeglAeQ4CeQ4whhlAegWCewDBexhQaQpw?hA8AeA8whQ4xwQ4JeAABeA8FeA8AeAAQeMPBvhETOBuNBzE?BlbBpFBsfwhIewhQeRpFeh0SpEeg0QpAewhQpQ4CeQ4AewS?AewhgWR4BeQ4whgWQahWwhB8AewhglxwAtg0wDwhB8xhQ4i?0BeAAA8FeA8DeAAFeA8BeAANeiDBvhC2/AT5A/1AVfQ4JeQ?4AewhDeRpQaJeQag0EeRaAPxSFeBtBeQpDegWAewSAewhAe?Q4CeQ4DegWAeQ4CewhgWQagWAexhA8AexhQ4i0AABeA8FeA?8AeAAIeA8BeAASeA8BeAAKe0GBvhKlHB5MBmQBZQBPbBUbB?lbBaeBTaBybB9ZBAgwwDeglCexwRpilAeQ4hlwwRpywAeR4?glwhE8AeC8QpD8g0AeB8Rpxwi0A8whg0gWQpwSwwSpA8xhg?0Q4AADeA8IeA8AeAAIeAAA8KeT8AvhP89AO9ApNB3TBsSBC?TBzMB+QBFZBZaBvcB+jByiB3YBLPBpJBqfwhIewhIewhRpH?eRpDeQ4Beg0Q4EeS4g0QaQpwhg0AeRpQ4glwhQaQpwhg0Ae?QpAPhlQLQag0D8yhwDg0glQ4glA8xwwhg0gWg0glQ4glA8x?wg0gWg0hlFeAAA8EeAACeA8LeMGBvhDzDBFMBxHBPEByfQ4?AewhHeQ4EeyhgHBeRpAeAtSaglAPCeAtCeQ4Beg0AtxSglA?eQpAPRaQLQag0Q4glA8xwh0gWhlPeAAA8OeAABeA8FeA8Be?AAMeMBBvhDyHBNMB9PBGMBufi0AeQ4AewhAewwAeAtwwg0g?lR4whxwG8AeI8AeC8ilA8whAeQ4A8QpA8AtQpglg0whwDQ4?RpCeAACeA8HeAAA8BeAAEeA8CeAAEeA8FeA8DeAAGeA8AeA?AKeZyAvhJzyAf5AM7AKvA2tAGpAd6AX2A5/ATABrfRpj0gl?CeRpj0glBewhBtjlg0A8AeA8xwjlg0A8AeQ4xhCeAABeA8M?eAABeA8FeAAEeA8CeA8GeAAAeA8GeAAIeA8AAHeA8AAJeaE?BvhISJB8HBOOBZQBzVB/RBUUBdNBTLBdfwwEeRpQ4xwEeRp?R4wwBeBtAewhRpQ4AtAeh0BtwhRpBtAeg0glRpD8QpC8AeA?8xwwhRpC8AeA8xwxhQpB8BtAeQ4xwwhAtA8hlAtAPQ4xwBt?Aeglg0xwEeAABeA8FeAABeA8FeAACeA8EeAACeA8Je92Avh?Qu+Aq+A//A89Az8AO8AJFBCKBfCBtCBUABZ3AFEBuDBpPBS?UBPRBLgwhQ4GeglBeQ4Beh0hlDewwBegHhlQaQ4xhQpA8gH?A8BtQ4neAADeA8Ne0HBvhNTBBMKBFOBaNBphBumBnnBTeBT?dBUgBFhBZaBPlBSoBpgwhHeglwhQ4EeilAeR4CeSpwhQawh?E8i0Q4wDwhC8zwA8CeAAOeTPB1fRpHeRpwhHeglwhQ4Eeil?whR4CeRpCeG8xwQ4AeG8g0Q4whB8AeB8i0Q4xhC8wwwSxwA?8FeAACeA8EeAAFeA8BeAALe0HBvhA2EBrfRpFeg0CewhFeg?0QpQaAeQ4BtBegHBPCeQpAeAtAeQpyhQaxhBtA8zwA8AAIe?A8BeAAIeA8BeAAmeA8AeAAJeW9AvhA/1ALfQ4IeR4RpAeg0?DeQaQ4Rpwhi0BeRaQpQaxhBtBegHQLRaAexhgWAtAeySwhQ?awhBtC8i0Q4wDwhBtA8zwA8CeAAFeA8EeAAFeA8BeAAceA8?AeAAEeAAAeA8GeAACeA8Jet5AvhGa5AZ3AG+A01Ad9A58A/?CBeewhQ4HewhR4BeBtBexhwwQ4BeQ4BtAeD8AeL8AeI8AeI?8AeI8AeK8AeA8Q4whB8AeE8Q4xhA8AeBtB8R4QpwhA8Aewh?BtA8DeA8AAIeAAAeA8GeAAAeA8GeAAAeA8IeA8AAIeAAA8E?eA8BeAAFeA8BeAAKeTWAvhHvYAOUAibA5rA62A91ATsAXpA?HfR4DeRpAeQ4AewDCewwBeg0QawDCewwAeRaAeQLQ4Aehlw?SAewwAeglQ4whA8g0gWg0Qpwhg0AfA8BeAAgeAAEeA8KeMj?AyeAtHeAtAeR4DeRpAeQpAewDCewwBeglQawDCewwAeRaAe?QLQ4AehlwSAewwAeglQ4whA8g0gWg0Qpwhg0AfA8BeAAgeA?AEeA8CeA8EeAAKe9pAvhOpIB2+AS/AT7AU4AO3AM6ApIBNN?B/MBTKBKBBq8AzCB34AkfglAeR4EeglAeQ4AewDGeQLAPQp?GewwEeRpBtAeAtwSBeAtCegWwSAewwAeAtCeglBtwhQpA8B?twSwwfeAAEeA8CeA8EeAAHeAAA8KelIBvhAAAA";
        String command = String.format("util fig -t %s -n 6 -d 10 -L false -H hidden", tetfu);
        RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

        ByteSource actual = FigureFileHelper.loadGifByteSource();
        ByteSource expect = FigureFileHelper.loadResourceByteSource(UtilFigResources.TetfuCase2);

        assertThat(actual.contentEquals(expect)).isTrue();
    }

    @Test
    void useTetfuCase3() throws Exception {
        // オプション: next, delay, frame:no

        String tetfu = "v115@vhWRQYgBFLDmClcJSAVDEHBEooRBJoAVBTnjWC0PNP?CsuLuCPHOMC6vTWC0/TFDTuKWCJ3jPC0HztC0n9VCKn/wCT?+TFDUe/VCzHDMC6yytCqubMCvvTWC0nltCsuHgC3mBTpBcq?BykB+tBlsBetBpjBKkBUnBFrBzpBXrBzhBZaB8lBlsBXqBC?lB+oBWgBiVBLgilGeglg0Hewhi0Eeg0QaBeglEeg0QaAPR4?AtRpAeh0AexhgWAtRpBeQLQ4i0AeD8glQag0xhAtxwA8hlQ?axhBtxwB8Q4JesSBvhBlYBXUBBgilGeglg0DeAtAeR4QaBt?g0AeBtR4g0QaAPhlAeAtwhwwgWQaAexhgWAtRaxDQLQ4i0A?eAtSpglQaxhBtxwB8Q4AACeA8EeA8FeAABeA8FeAALexHBv?hDCFBzDB7CBpABsfwhWeTpCewhh0QpwSxhR4wwAeCPBewhh?WBexwglAtQagWSLQ4xhAtAPxwB8Q4AACeA8OeA8FeAAQeAA?AeA8LeeABvhAX9AEfwhHeg0whBeR4Deg0whAeR4Tph0xhi0?AeBtR4wwwhilAeAtxwQawwwhQ4AewSAtRpAegHAeA8xhwww?SxwhlQ4QailA8BtxhglQai0A8AtSpglQ4xhBtxwAeA8Q4De?A8BeAABeA8DeAADeA8CeAAIeAABeA8FeAABeA8FeAAA8NeN?oAvhATrAwewhwwEeRpg0whxwR4BeRpg0xhi0AeBtR4wwAPi?lAeAthlQawwAPglAewSAtQpAewSgHAeQ4ilAeBtxhglQai0?A8AtSpglQaxhBtxwB8Q4DeA8BeAABeA8FeAABeA8DeAAHeA?ABeA8FeAABeA8FeAAA8OeAABeA8IeA8AAHeA8AANeMgAvhC?dlA0jA/rABfQ4CeBtBewwBeQ4AewhwSQpQ4AtQaQpAPCeQ4?xhAtAPxwwhA8Q4AACeA8OeA8FeAAQeAAAeA8FeAAA8ieAAB?eA8FeA8CeAASeA8AeAALeanAvhJulATkAZlAOyA92A56AK8?AU9AtDBfFBWfQ4BeglDewwAeR4AeglBtAewhxwwhQ4wwD8A?eI8AeB8whB8g0C8AeQpA8xhA8g0BtA8QaRpQ4whQpAeAABe?A8IeA8AAHeA8AAHeA8BeAAMeAACeA8EeAACeA8EeAAEeA8L?eGuAvhBZ+APEByfQ4JeR4Begli0AegWBeQ4Aeg0gHhlA8Qp?yhA8AeA8CeAASeA8AeAACeAAEeA8geA8FeAAKea+AvhCU1A?p7AzzA0eRpHeRpBtBewhCehlQ4BtAewhDeglS4AewhA8AeK?8xwAeG8xwAPAtB8Q4C8h0whAtAPA8Q4A8AeB8g0yhA8Q4Ke?AACeA8EeAACeA8KeA8AABeA8DeAADeA8DeAADeA8DeAADeA?8DeAAIeAAAeA8KeanAqeRpJeBthlwhCeRaQpAeglEegWBeR?pBeA8AeB8g0yhg0Q4AeA8CeAASeA8AeAACeAAEeA8geA8Fe?AAGeAAAeA88eTTAvhIvjA2eAEkA0jApqATwA1zAG1Af2AQe?Q4IeR4DewwAei0Q4AeA8AeI8AeI8AeI8AeP8AeG8AewhH8A?exhD8QpA8glgHglwhA8AeA8DeAADeA8DeAADeA8DeAADeA8?DeAAIeAAAeA8MeAACeA8GeAAAeA8GeAAAeA8GeAAAeA8CeA?AEeA8Me6UAvhAcVACeAtCeQ4DeBtCeR4AehlAtwwAei0Q4A?eA8AeA8AtC8whC8AeBtC8xhA8h0AtQpA8ilwDA8AeA8DeAA?DeA8DeAAIeAAAeA8+eAACeA8GeAAAeA8GeAAAeA8EeA8AeA?AEeAACeA8IeA8CeAAEeA8BeAABeA8FeAALeAAA";
        String command = String.format("util fig -t %s -n 1 -d 50 -f no", tetfu);
        RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

        ByteSource actual = FigureFileHelper.loadGifByteSource();
        ByteSource expect = FigureFileHelper.loadResourceByteSource(UtilFigResources.TetfuCase3);

        assertThat(actual.contentEquals(expect)).isTrue();
    }

    @Test
    void useTetfuCase4() throws Exception {
        // オプション: next, delay, frame:right, start:10, end:30

        String tetfu = "v115@vhKSSYXDFLDmClcJSAVDEHBEooRBMoAVBTn/wCpXEx?Cz33LCvi3LCa3TxCqXstCJHWWCvfbMCz+jPCaX9wCsfjxCT?uKWCUentC6yytC0/TFDJn/VCUuaFDTnLuCqCOMC0vCMCaN8?LC0/LgCaN+tCvPNFDsOUFDTXNPCaentC0yCMCz3HgCsOegC?pHkPCsuntCpirgCat3LC0ySgCqe9VCzXUFDUeHgCzyLMCqu?rgCzeFgCs3/wC6vTWCJnLuCMe/wCvC+tCan3LC03HgCT+jB?A3qBznBGjBtpBZkBUgBymBfsBErB2uBLgg0Iei0GeI8AeC8?AeI8AeI8AeI8AeF8glB8AeF8ilAeF8Je9PBvhM5lBzaBiVB?2mB8iBzcBvZB9YB2QBMXB5lBiiBpeBegwhIewhg0EeilAei?0BeAtglwwQ4AeAPhlAeBtRpwwg0APxSAeAtQawhAewSg0gW?h0A8BtRpxhgWxwA8AtxwQpAtwhJezOBvhRNXB/QBaRBzNB8?MB5bB+jB3iBlhBWlB3bBMZBZkBTpBiqB2kB0mBlgBVgg0Ae?ywEei0wwBtCeAtRpilBtAeBtRpglR4CeAtg0glA8SpD8Aei?lQpBtC8APxwi0BtA8AtAPxwg0xhC8AtgHJe/GBvhERCBTKB?lTBSdBhWBjfQ4zhEeR4zhBeRpg0Q4ywBeglRpI8AeI8AeI8?AewhT4D8AewhwDT4B8xwglwhQLRpB8g0xwBeAAFeA8BeAAF?eA8BeAAFeA8FeAABeA8Je2tAFfg0Jeh0GewhRLxhFeg0Cew?hBeRpQaAeCtQaAeglBeglwhSpB8g0wwwSeeAAGeA8AeA8AA?meA8CeAAIeA8AeAAKeH1AvhA00APfg0Jeh0AeBtDewhRLwh?wSAeAtCewhT4BtB8feAAGeA8AeA8AAmeA8CeAAIeA8AeAAU?e67AvhO96AvABt9Az+A6FBG/AsGBTGBOIBJWBKbBtWBTVBU?eBpZBAgwhCewwEewhRpglxwDewhRpglwwh0AeBtwhF8AeB8?Q4C8QpD8AeQ4xwg0RpC8AeQ4xwg0QpglgHA8BtQ4EeAAA8H?eAABeA8FeAABeA8KefFBvhDc9AT8A23A/tAdeQ4IeR4Heg0?Q4Hei0AtFeRpBtCeQ4AewhAPQpAtwwCeR4whBPglxwCeQ4w?hwSAPglwwh0AeBtwhilAtB8AeC8xwBtC8whAeQ4xwAtQpC8?whwDQ4xwg0RpAeB8whQ4xwg0QpglgHA8BtQ4EeAAA8HeAAB?eA8FeAABeA8yeaVAvhCFwAJwAFxAxeQ4hlHewwHeQaJeQaB?tBewhywxSgWDeglAeAPCewhDeglAexwAtQpB8Q4whwDQ4Oe?AABeA8SfAABeA8NeTvAvhS2eAUvAJ2A67AphBtHB+OBSFBX?7A03ATxAf+AlNBzNBmGBphBciB6mBXnBDhR4Rpi0BeQ4Aew?SBehlgHB8xhAtxwBeA8EeAAKeSeBvhAMUBUgAtHeAtJegWG?eglgWGeQ4wwxhi0BeQ4AewSBehlgHB8xhAtxwBeA8EeAAUe?XcBvhJTaBtbB+QB0XBJbBuiBZVBPiBylBFiBfgwhIewhDey?wCeg0BeglQ4wwh0AtQ4D8SpAeA8Q4glB8g0whQpglgHAtFe?AAAeA8GeAAAeA8KeTWBvhBfXBsVB5fAtQ4FewhBtR4EewhA?tRpQ4ywBeH8AeC8AtwhD8AeA8Q4BtxhA8AeC8Q4AtxwwhQp?QLQpB8EeAABeA8FeAABeA8FeAAA8HeAAA8MeOPBvhATPBNg?AtQ4DeRpwhAtBeQ4EeQ4BtxhA8AeA8xwOeAAA8keAAA8NeC?FBvhGFOBJOBTQBphBMrBXrBWtBEhg0FeR4whAeh0RpAeQ4A?ewSAeCPwwwSA8xhAtQ4i0JeNfBLgwwIexwEeg0BewwCeR4w?hi0RpAeR4AtwhilQpAeH8RpA8AeC8glB8QpB8AexhQ4ilxw?A8wDwhAtQ4i0AeA8AeAAPeFRBvhCyXBfUB+RBmfg0CeQ4Be?wwBeg0BeglR4Aexwh0ilg0Q4AeA8AeK8AeI8gHC8whB8QpB?8gHB8g0xhA8RpglgHi0glwhA8AeA8BeAAHeA8AAHeA8AAHe?A8AAHeA8AAOe67AvhGs8AT+AM/A38A5MBZmB+oB8gg0GeAt?CeR4CeAtAeg0AeA8xhA8AeA8BthlDeA8DeAAJeifBvhNzcB?FmBOoBzlB9nBKkBXdBUhBScBUTBFlBJjBelBTfBugBtAewh?AeRpg0BegWAeAtEehlAeRpBeQaQpAehlwwAegWBeg0hlBtx?hQpQ4EeAADeA8Je/WBvhHaXBpjBvoBGnBpZBNkBskB3nBKg?whIewhBeAtDeR4whwwBtCeS4whD8AeE8AeH8Q4AeH8Q4AeA?8AtD8xhQ4QLBtC8yhQ4CeAAA8OezIBvhEFSBZfBKkBziBOm?BVgglCeh0AeRpwhglCeg0TpwhhlAtAeg0RpR4whC8AeF8g0?C8hlA8wSwwQ4g0C8glxwwSwwQ4h0AtA8glxwwDwhQ4CeA8C?eAALeMJBvhV0GBlDB+FBfABy+AT2ApnA30A8wAivAxBBlHB?WRB6WBNVBTWB/VBUWBGdBzbB5nBAAA";
        String command = String.format("util fig -t %s -n 9 -d 21 -f right -s 10 -e 30", tetfu);
        RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

        ByteSource actual = FigureFileHelper.loadGifByteSource();
        ByteSource expect = FigureFileHelper.loadResourceByteSource(UtilFigResources.TetfuCase4);

        assertThat(actual.contentEquals(expect)).isTrue();
    }

    @Test
    void useTetfuCase5() throws Exception {
        // オプション: next, delay, frame:right, start:3, end:158, hold

        String tetfu = "v115@vhKWSYzDFLDmClcJSAVDEHBEooRBUoAVBqybgCpHUx?CMn3LC6ubMCKHstCPt/VCa+VxCJNMgCqHUWCqubMCPtbMCq?+KWCTHUPC0nFgCqO8LCzHztCpCmPCan/wCJtjWCzuzPCpya?PCM3LMC6uzPCT+VWCvnNPCv+TWCa+TPCU9aPCP+9tCs3zPC?0yLMCvvjFDvS9wC0fjWCPujPCvHMMCs+TWCUuaPCat/VCpH?LxCPNWWC6PltC0i3LCanbMCKHExCTNUPCp+aFD6vbgCpOst?CUnjWCpf/wC6ybgCJtzPCMuKxC0SNPCa9qBASwBXmBTpBJn?BUoBVhBvXB6WBzdBOfB5fhlQ4HeglR4Deh0AeglwwQ4AeRp?Aeg0BeywwhRpAeg0AehWAeQ4whBtAeRpAewwAeQLwhg0Btx?SBewSQLwhRpg0glB8RpQLQ4xwA8glC8whwDQ4BtA8xwA8xh?gWQ4glBtxwA8h0gWQ4ilJepKBvhEMEBFRBMKBdWBeTBRgg0?LeAtBehlQ4g0AeBtCegWBewhgWBewSC8g0whwDCtQ4ieA8A?eAALeTQBvhEiRBphBTkBvmBmfBpgi0AeQ4EeRpg0AeR4g0C?exSAPglQLQ4g0BeAtxwglA8xhglAeB8xwh0gWwhglB8AtDe?AABeA8Le6WBvhBMeB3LBCgR4GeR4hlFeg0RaglQ4DeAtBPA?tAeR4g0AeBtxSAPAeQLQ4g0AeAtAexwglg0xhglAeBtxwh0?gWwhglA8BtDeAABeA8FeA8AeAAGeA8AeAAMelIBvhBlOBpK?BAgwhKeR4GeQ4AeQLglywCeBPBewSglwhAeAtQpxwi0whgl?AeBtDeAABeA8PeA8AeAASeA8BeAAJeZ8AvhEq8ATtAuTBUT?BfUBZfRpHeRpHewhglHegWQaEeQ4AewhgWQaglBeBtR4whA?ewwQ4CeBtQ4whQ4g0B8AeB8whA8R4h0A8AeBtxhR4xhC8AP?AtwhQ4DeA8AeAAGeA8DeAADeAAAeA8GeAAAeA8GeAADeA8J?eX6AvhEa7AFIB0HBONBTMB3fRpHeTpCehlAegWQaRph0R4g?lAezwB8Aeh0A8Q4g0xwhlwDwhg0A8FeA8BeAADeAAAeA8Ge?AADeA8NeA8DeAADeA8DeAAJepABvhEUDBfABm8AFEBT8Atf?RpOeQ4BeRpg0EeQ4whilC8AexhQ4PeA8BeAADeAADeA8XeA?8DeAAReAAA8JeSDBvhHJEBvPBKMBGKBTBBUJBFNBTBBPfRp?HeRpHeRpHeRpglBeBti0D8AeE8xwB8AeE8xwB8AeE8xwG8A?exwg0B8BthlgHDeA8CeAAEeA8BeAAFeA8BeAAIeAAAeA8Ge?AAAeA8GeAAA8KeuzAvhCJ+A6PB1SBjfRpHeRpAeh0wwDeRp?Aeg0ywAehlH8AeA8xwE8AeB8xwA8hlQpA8AeB8xwA8glSpA?eh0GeAAA8QeAAA8HeAAA8HeAAA8HeAAA8Lev6AvhUJzAMAB?T3A5TBSXBXNBtYB+ZBzTBURBWSBJnBfcBUeBifBFiBvhB9j?B2kB8dBqaBggglFeAtEeQ4BeAtAewwg0AeglQ4AeQ4BewhB?eBtEeQaAexSgWwSCeRawhBewSQpAeQLAeglQpAexwQ4xhgW?A8QphlJeTRBvhgOQBJiBTVBvjBKbBMnBZcBMdBuWB9ZBXPB?pmBykBTaB0bB1WBOnBpUBzYB3QBlcBidBpmBUrB2sBSoBNp?BvrBTfBziBGeBFqBvrBjgQ4Bei0RpBeR4AeRpg0RpBeR4Ae?RpglD8AeI8wDB8ilxwB8wDwhA8xwglxwB8wDwhA8xwg0Jea?YBvhAhOBRgzhDeQ4glwhywRpCewwAexSJeAPxwB8wDwhg0x?wg0deAACeA8OecRBvhB2QBsLBlfAtHeBtGeg0AtDezhRpAt?AeR4glRpglB8AtA8AeF8BtA8AeE8glAtB8AeA8T4xwAtA8w?Dwhg0xwg0AACeA8EeAACeA8EeAACeA8EeAACeA8YeS7AvhG?99AZNBTSBXPBPTB0MBTJBffRpDeBtAeQ4RpR4BeCtT4glAe?BtwwRpQ4ilAeI8AeE8xwB8AeA8BtA8whxwxhAeA8Ctzhg0A?eBtQpxwwhi07e+sAvhOZkBNLBCMB2JBMNBiLBzMBZkBXjBN?kBTiB+eBvmB8bBCdBjgAtDeg0CeAtAeilFeQpAexhg0Aeww?BeAPAewwBexhAewwAeQpAPAeQpAtAeQaQLQpA8xwwhglxhg?lTeFgBvhV/fBSdBRXBRSBzIBOeBFbBPgB+ZB8YBCPBcWBZk?BTkBTZBPdB9YBKPBMOBmfBFhBpPBKgwhQeAtglGeAtJewhA?ewhEeQ4whAexhFeglCeRpBtAeBPQawhQpwSAtAewSwhhWAe?glAeA8whAtT4AtgHglJePhBvhDykBNkBmlBphB2fwhEewhC?ewhEewhAeAtglwhEewhBtglwhwwAei0whAtwwhlH8AeJ8Qa?E8Q4C8QaE8Q4A8Atg0QaE8Q4Btg0QaQpA8ilQ4AtQpg0gWF?eAAAeA8KesGBvhZMGBzHB2HBz9AiJBNGBfPBXHB6NBOOBpj?B9oBTkBMoBxqB2kB8nBzgBXbB9eB6hB3VB5YBNQBMUBSXBL?gwwHeAtAewwCeglAewhAtBeglQ4hlDewhglBewwDewhAeQa?BeAPwhCeQaBeRaDeQpAeQaxSQLhWQaDexwT4AeCtAeAADeA?8MeJiBvhB2gBzYBIgRpAewwAeg0DeRpAtxwi0glAewhBtww?R4ilAewhAtwwT4hlAegWglwwQpAeglD8xwAtRpilg0A8QaB?tQpxhi0A8QaAtQpzhh0A8QaRpAeA8EeAALeGABvhNlBBSIB?X+AT3AJOB3MBUKBmGB0CBpABz/AFOBdTBAAA";
        String command = String.format("util fig -t %s -n 7 -d 17 -f right -s 3 -e 158 -H hidden", tetfu);
        RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

        ByteSource actual = FigureFileHelper.loadGifByteSource();
        ByteSource expect = FigureFileHelper.loadResourceByteSource(UtilFigResources.TetfuCase5);

        assertThat(actual.contentEquals(expect)).isTrue();
    }

    @Test
    void useTetfuCase6() throws Exception {
        // オプション: frame:no, line:4

        String tetfu = "v115@vhLNJYkBFLDmClcJSAVDEHBEooRBUoAVBvHkPCpC8L?Cq+jFDvvbgCa+VWCvu/VCpHkFDv/NMCzCWWCaXNPCzn9wCz?vKWCUuKWCqHMMCvXUPCUtPFDPN8LCUNUPCUXNFDUentC0fB?XqBWyBToBCjBJnBarBpmBlqB2uB/kBBgQ4IeR4Heg0Q4Hei?0GeI8AeI8AewhH8AexhG8AeglwhG8AeilF8Ke0MBvhKTOBv?NBGDBECBpoBTjB6tBTsB1pB+tByxBIhwwFeglg0wwAewwCe?hlBeSpC8h0gWglJeZaBfgwhdewwFeglg0AtAewwCehlBeSp?C8h0gWglCeAADeA8KeUbBVgwhTeBtHewhAeAtDeglg0AtAe?whAPBehlBeSpC8h0gWglCeAADeA8Ue/YBvhFlcBzbBMjBuh?BllBXoB9gwhDeh0AeR4BeywAewhQ4AewSQ4A8QpQLQpglyh?AtCeA8DeAAKe6fBvhDllB5iBMtBywB6gwhHeglwhAeAthlB?eilAeBtF8g0Q4A8APh0B8i0Q4AtAPJeTaBvhRugBncBJbB0?WBNjB3iB2bBTfBKVBpoBlrBxxB6kBWtB8lBTjBpUBTVBKgw?hIewhRpHeRpHeRaBeAtCeQpglRaAeBtBeg0QpglBeg0AtAP?Beg0RpBeg0hlAewhQLhWh0glAtAeB8ilh0hlgHA8T4JecWB?vhDiMB3TB2GB/8AjfQ4JeQ4HeQaHewhAeQag0GexSAPhlHe?glgWBeR4AeRaglAeAtAeQ4AeAPglFewDQaDeglAeAPCeRpC?eglg0AewhSLh0hlgHA8T4KeAABeA8OeeFBvhB09AT2AZfQ4?GeRpAeQ4HeQaAeBtDeQpgWAeQaglAeAtEexSAPhWBeg0Eeg?lgWBexhAeRaglAeAtAeQ4AeAPglFewDQaDeglAeAPCeRpCe?glg0AewhSLh0hlgHA8T4KeAABeA8FeA8FeAAKePuAvhH6uA?lIBlXBXTBCTBZNBFYBuXBmhI8AoFvhAAAA";
        String command = String.format("util fig -t %s -f no -l 4", tetfu);
        RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

        ByteSource actual = FigureFileHelper.loadGifByteSource();
        ByteSource expect = FigureFileHelper.loadResourceByteSource(UtilFigResources.TetfuCase6);

        assertThat(actual.contentEquals(expect)).isTrue();
    }

    @Test
    void useTetfuCase7() throws Exception {
        // オプション: line:4, next:9

        String tetfu = "v115@vhKyOYPCFLDmClcJSAVDEHBEooRBMoAVBvP9wCzXst?CpuPFDPn/wCJNUFDsuPFDPdNFDzCOMCvvKxCzfbMCs+ytC6?i3LCTnjxCK+DxCJ9aFDqiHgCzn9VC0X+tCqyCMCUdNPCaHU?PCpHcgCseltCs/dgCa9KWC033LCUdNFDv/TPCJ9KxCaHstC?pX2BATkBmfB9tBXjB0sBRmBqqBvrBFsBWwBOgg0Iei0DeE8?AeF8AeI8AeI8AeI8AeI8AeglH8AeilD8JexOBvhGzGBTFBO?GBsNB5JBFNByNBpfglwhFeilwhBeh0AeywAtwhRpB8AeI8A?eC8g0Q4D8AeA8i0Q4B8hlAeSpAtQ4xwVeA8DeAADeA8DeAA?DeA8DeAADeA8DeAALecyAoeAtHeBtDeglwhBeAtCeilwhBe?h0AeywAtwhRpB8AeH8AtAeG8BtAeC8g0Q4B8AtA8AeA8i0Q?4B8hlAeSpAtQ4xwLeA8DeAADeA8DeAADeA8DeAADeA8DeAA?NeAADeA8DeAADeA8DeAADeA8DeAADeA8Le3WAvhUFfAOUA8?aATiASiAXYAJ4AXrASnAFuAczAT0AJ4AT8A23AX5AGzAM7A?d/ApOBKIBdfglIeglEej0hlCeAtj0R4wwAeBtRpwhQ4ywAe?AtglB8AeA8g0E8hlgHglh0C8AthlgHglxhQpA8BtxwQaxhR?pA8Atg0LeA8AAHeA8AAHeA8AAHeA8AAPeTxAvhCl6A//AC7?AqfilDeglAeQ4AexhGewwBeB8AeA8g0A8xhxwpeA8AAmeA8?CeAALeu5AvhBJMBcLB4fAtCeh0ilBtBeglg0Q4glRpAtAPB?eglAewhwwxhBtAeA8g0glwhg0xwAtB8Aeg0glxhxwBeA8AA?ceA8CeAAEeA8EeAAGeA8AeAAJeT2AhfRpKeAtCeh0glRaAt?CeglAewhAexhAeAPEewwBeAtA8AeA8g0glxhxwBeA8AAmeA?8CeAAIeA8AeAANeAADeA8JeX0AvhDUwAOyAFzAC2Agfilh0?CeBtAexhhWBehlQpDeAPEewwBeAtA8AeA8g0glxhxwBeA8A?AmeA8CeAAIeA8AeAANeAADeA8DeA8AANeF9AvhH5MB3RBTL?BMRB2XB6YBpeBmfBzgi0CehlAewhxSAeAtg0AegWCexwglA?tgHB8g0A8Q4DeA8AAIeA8BeAAKeTQBvhAkcBLgRpHeRpHeR?pg0AeBthlAewhBeg0Atg0BtglAewhilA8APAth0A8Q4xwgl?AtglAPAtg0A8Q4DeA8CeAAPeAABeA8Ke/MBvhBaUBpPBsfw?hIewhRpQ4FewhRpR4CehlwhD8AeD8Q4E8AeC8Q4xwwhE8Ae?Q4xwxhA8AeA8h0Q4DeA8AADeAADeA8DeAAGeA8AeAADeA8D?eAADeA8NeNDBvhA89AnfAtDewhCeAtFeRpQ4AewhHeQpAew?wAehlAexwxhQpQLA8h0Q4EeA8BeAAFeAABeA8KeAADeA8he?A8CeAAOef7AvhFF/AyBBT3AmyAtDBPEBZfi0BeQ4DeRpg0B?eR4CeRpglAeAtwwR4AewhilB8whB8AeA8xwglB8wDwhC8xw?g0A8AtQLxhA8Q4AAGeA8AeAADeA8DeAADeA8NeA8CeAAEeA?8HeAAA8HeAADeA8DeAAJe5uAvhDO7Ac1AztAC7AoeRpDeAt?CeRpwhBeBtBei0whAeQ4AtilRpg0whAeR4glh0E8AeE8xwB?8AeA8AtB8AexwQ4B8BtB8gHhlQ4A8whAti0wSwwglQ4A8xh?g0hlAADeA8HeAAA8DeA8HeAAA8HeAAA8HeAAA8CeAASeAAD?eA8DeAADeA8DeAADeA8JeOgAvhGZZA0aAPWATiAplAirAto?AeewwAeQ4FewhxwR4EewhwwBtQ4whilBeI8AeQpA8whF8Qa?RpxhE8QaQpBtwhQ4i0B8CeAAEeA8HeAAA8HeAAA8HeAANeA?ADeA8DeAADeA8DeAADeA8NeA8DeAADeA8DeAADeA8DeAADe?A8DeAAJevYAvhDScAZjAOoAdpA7eh0wwHeQaKeQ4CeglQ4A?eglRpwhAeB8g0whA8DeA8DeAAXeAADeA8reA8DeAAdeAAHe?A8JeMqAvhAvsAxeh0wwEeQ4Aeg0xwBeAtBeR4AeQaAeQ4Bt?AeglAeQ4glRpB8AtB8whwDglRpwhBtA8g0whwDNeAADeA8D?eAADeA8heA8DeAADeA8DeAATeAAHeA8JeA8HeAAJezaAneh?0wwRpCeQ4BeQaCeAtCeQ4CeAtwhBeglBeglRpwhBtA8g0wh?wDXeAADeA8reA8DeAAdeAAHeB8HeAAMeAAEeA8JeTKAvhRN?cAsbA2aApnAtrAvsAZ+AMDBK/AGCBiBBGFBT3A/9Az6ANDB?sDBAAA";
        String command = String.format("util fig -t %s -l 4 -n 9", tetfu);
        RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

        ByteSource actual = FigureFileHelper.loadGifByteSource();
        ByteSource expect = FigureFileHelper.loadResourceByteSource(UtilFigResources.TetfuCase7);

        assertThat(actual.contentEquals(expect)).isTrue();
    }

    @Test
    void useTetfuCase8() throws Exception {
        // オプション: next:-1

        String tetfu = "v115@vhTRQY2AFLDmClcJSAVDEHBEooRBUoAVBpiHgCzHkF?D0vCMCsC+tCp3HgCMeLuCq+aPCTuKxC6CBAAKpBWyBToBPn?BUmBveBFqB0pB2qBTfBlsBywB6sBpjBvmBlqBsmBmsBzXBa?gRpHeRpHewSwhHexhBewhDeQ4Atglh0whDeQ4QpgWglg0Ae?A8AeB8whAtilQ4A8AeB8xhh0glQ4JeqYBvhB5kBZkBefRpH?eRpglBewhEeAtglBewhDeBthlyhBeQ4Ati0yhBeR4hlg0wh?F8AeI8AeH8wwwSH8wwwSg0B8Q4E8APg0B8Q4D8AtAPh0S4B?8whAtgHhlS4B8xhgWg0glQ4JeU9AvhCH+A+CBTFBieRpFeg?0AeRpglRpwhBeg0R4AtglRpF8AeI8AeI8AeI8AeI8AeI8Ae?H8wwwSF8glA8wwwSg0xwQ4A8AeglxhAtg0xwBeAACeA8EeA?ACeA8EeAACeA8IeA8BeAAFeA8BeAAFeA8BeAAFeA8BeAAFe?A8BeAAJe9UAwhwwAeg0R4AtglRpF8AeI8AeD8QpC8wwwSC8?RpA8glA8wwwSg0xwQ4QpA8glxhAPg0xwfeAACeA8EeAACeA?8EeAACeA8EeAACeA8IeA8BeAABeA8FeAABeA8FeAABeA8Fe?AABeA8FeAAHeAAA8HeAAA8HeAAA8HeAAA8HeAAA8JeAAA";
        String command = String.format("util fig -t %s -n -1", tetfu);
        RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

        ByteSource actual = FigureFileHelper.loadGifByteSource();
        ByteSource expect = FigureFileHelper.loadResourceByteSource(UtilFigResources.TetfuCase8);

        assertThat(actual.contentEquals(expect)).isTrue();
    }

    @Test
    void pngCase1() throws Exception {
        // オプション: format:png

        String tetfu = "v115@vhPRQYGBFLDmClcJSAVDEHBEooRBUoAVBJ3jPC6ijW?CTuKxCqiLuCJn/wCPt/wCsn1LCaHUPCUdNPCUnLuCMtPFDU?X9VC0/AAATpB3mB+tBcqBykBlsBKkBXtBUnBRiBNrBuqBSy?BzpB0qBrgBtIeBtEeI8AeI8AeB8BtE8AeC8BtD8KexdBvhA?vXBZgQ4GeBtAeQ4FeAPAewSAezhC8BtwhS4QaleAAA8Je2V?BvhATTBFgQ4AeRpBeg0AeBtR4RpBeI8AeD8whA8xwA8Aegl?A8BtxhxwA8SeAAA8GeAAAeA8GeAAAeA8GeAAA8KelBBvhBv?KBGHBCgi0FewwRaAeQ4AeRpQ4AeSpglwhA8xwwhceAAA8Ge?AAA8beA8AAKelDBvhAi8AFfilGegli0ywCeywg0Q4wwRpQ4?AeI8AeI8Aei0F8Aeg0ilSpA8AeA8SpglwhQpwwwSwhA8GeA?AAeA8GeAAAeA8HeAAA8FeAAAeA8GeAAA8HeAAA8HeAAA8He?AAAeA8KeTZAvhDp2AMAB5JB0MBZfRpHeRpBtFeilBtDeAtH?8AeG8AeC8xwD8AeC8xwBtB8AeC8i0BtA8AeB8AtFeAAAeA8?CeAACeA8EeAACeA8EeAACeA8EeAACeA8We6xAvehlRpGegl?RpBtEejlBtDeAtH8gWg0xwD8AeB8g0xwBtB8AeB8j0BtA8A?eB8AtFeAAAeA8MeAACeA8EeAACeA8EeAACeA8EeAACeA8Ee?A8CeAAEeA8DeAADeA8DeAADeA8DeAAHeA8AALemaAvhFTPA?XhAdnApgAOwA1uAxeRpAewwAewhDeRpywwhBehlRpi0whh0?AeglxwA8QpA8Q4AeC8xwSpQ4AeA8h0xwilQ4gHglA8g0BeA?ACeA8EeAACeA8EeAACeA8OeA8CeAAEeA8DeAADeA8DeAAHe?A8AALeAAFeA8BeAAFeA8BeAAFeA8LeTcAvhEMiAfgAPfAar?AMsAAeT4hlAeBtF8AeE8AeI8AeI8AeI8AeM8AeJ8AeA8AtA?8whA8whC8AeBtA8zhh0AeBtFeA8AABeAAAeA8GeAAAeA8Ge?AAAeA8IeA8EeAAGeA8AeAAoeA8FeAABeA8GeAAAeA8FeAAL?eAAA";
        String command = String.format("util fig -F png -t %s -s 5 -e 30", tetfu);
        Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

        // 出力ディレクトリを取得
        String output = log.getOutput();
        String outputDirectory = extractOutputDirectory(output);

        // 出力されたPNGを取得
        List<ByteSource> actuals = java.nio.file.Files.walk(Paths.get(outputDirectory))
                .map(Path::toFile)
                .filter(File::isFile)
                .sorted(Comparator.comparing(File::getName))
                .map(Files::asByteSource)
                .collect(Collectors.toList());

        // Assertion
        List<ByteSource> expects = FigureFileHelper.loadResourcesByteSource(UtilFigResources.PngCase1);
        assertThat(actuals).hasSameSizeAs(expects);
        for (int index = 0; index < actuals.size(); index++)
            assertThat(actuals.get(index).contentEquals(expects.get(index))).isTrue();

        // 出力されたPNGを削除
        // noinspection ResultOfMethodCallIgnored
        new File(outputDirectory).delete();
    }

    private String extractOutputDirectory(String output) {
        Optional<String> optional = Arrays.stream(output.split(LINE_SEPARATOR))
                .filter(s -> s.contains("Output to"))
                .map(s -> s.substring(17))
                .findFirst();
        assert optional.isPresent();
        return optional.get();
    }

    @Test
    void pngCase2() throws Exception {
        // オプション: format:png

        String tetfu = "v115@vhK2OJzkBifB9tB0sBXjBplB2mBMrBXsBAAA";
        String command = String.format("util fig -F png -f no -t %s", tetfu);
        Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

        // 出力ディレクトリを取得
        String output = log.getOutput();
        String outputDirectory = extractOutputDirectory(output);

        // 出力されたPNGを取得
        List<ByteSource> actuals = java.nio.file.Files.walk(Paths.get(outputDirectory))
                .map(Path::toFile)
                .filter(File::isFile)
                .sorted(Comparator.comparing(File::getName))
                .map(Files::asByteSource)
                .collect(Collectors.toList());

        // Assertion
        List<ByteSource> expects = FigureFileHelper.loadResourcesByteSource(UtilFigResources.PngCase2);
        assertThat(actuals).hasSameSizeAs(expects);
        for (int index = 0; index < actuals.size(); index++)
            assertThat(actuals.get(index).contentEquals(expects.get(index))).isTrue();

        // 出力されたPNGを削除
        // noinspection ResultOfMethodCallIgnored
        new File(outputDirectory).delete();
    }
}
